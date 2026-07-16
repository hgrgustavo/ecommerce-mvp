package com.ecommerce.customer.accounts.infrastructure.configuration;

import static io.lettuce.core.ClientOptions.DisconnectedBehavior.DEFAULT;
import static java.nio.file.Path.of;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.SocketOptions.KeepAliveOptions;
import io.lettuce.core.SslOptions;
import io.lettuce.core.protocol.CommandType;
import io.lettuce.core.protocol.DecodeBufferPolicies;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=PRIVATE, makeFinal=false)
@Configuration
public class RedisConnectionConfiguration {
	@Value("${spring.data.redis.username}")
	String username;

	@Value("${spring.data.redis.port}")
	short port;

	@Value("${spring.data.redis.password}")
	String password;

	@Value("${spring.data.redis.host}")
	String host;

	@Value("${infra.redis.ssl.key-password}")
	String certKeyPassword;

	@Value("${infra.redis.ssl.cert-file}")
	String certificate;

	@Value("${infra.redis.ssl.cert-key}")
	String key;

	@Bean
	RedisConnectionFactory lettuceConnectionFactory() throws IOException {
		RedisStandaloneConfiguration standaloneConfiguration = getStandaloneConfiguration();
		LettuceClientConfiguration clientConfiguration = getClientConfiguration();

		return new LettuceConnectionFactory(standaloneConfiguration, clientConfiguration);
	}

	private LettuceClientConfiguration getClientConfiguration() {
		char[] certPassword = loadCertPassword();

		SocketOptions socketOptions = SocketOptions.builder()
				.connectTimeout(Duration.ofSeconds(3)) 
				.keepAlive(KeepAliveOptions.builder()
						.enable()
						.interval(Duration.ofMinutes(2))
						.build())
				.build();

		SslOptions sslOptions = SslOptions.builder()
				.keyManager(
						SslOptions.Resource.from(Path.of(certificate).toFile()),
						SslOptions.Resource.from(Path.of(key).toFile()),
						certPassword
						).build();

		ClientOptions clientOptions = ClientOptions.builder()
				.autoReconnect(true)
				.decodeBufferPolicy(DecodeBufferPolicies.ratio(1.5f))
				.disconnectedBehavior(DEFAULT)
				.pingBeforeActivateConnection(true)
				.replayFilter(command -> {
					CommandType type = (CommandType) command.getType();
					return type == CommandType.PING || 
							type == CommandType.GET || 
							type == CommandType.EXISTS;
				})
				.requestQueueSize(1000)
				.socketOptions(socketOptions)
				.sslOptions(sslOptions)
				.build();

		return LettuceClientConfiguration.builder()
				.clientName("lettuce")
				.clientOptions(clientOptions)
				.build();
	}

	private char[] loadCertPassword() {
		try {

			final byte[] bytes = Files.readAllBytes(of(certKeyPassword));
			return new String(bytes, StandardCharsets.ISO_8859_1)
					.trim()
					.toCharArray();
		} catch (IOException e) {
			throw new IllegalStateException("Critical failure reading the Redis certificate password.", e);
		}
	}

	private RedisStandaloneConfiguration getStandaloneConfiguration() {
		RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
		standaloneConfiguration.setHostName(host);
		standaloneConfiguration.setPassword(password);
		standaloneConfiguration.setPort(port);
		standaloneConfiguration.setUsername(username);
		return standaloneConfiguration;
	}
}