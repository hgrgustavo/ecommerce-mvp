package com.ecommerce.customer.accounts.infrastructure.configuration;

import static java.security.KeyPairGenerator.getInstance;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.ES256;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import com.ecommerce.customer.accounts.infrastructure.persistence.account.PasswordHasher;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.SpringPasswordHasher;
import com.ecommerce.customer.accounts.infrastructure.web.authorization.SpringJwtService;
import com.ecommerce.customer.accounts.usecases.login.JwtService;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.experimental.FieldDefaults;

@Configuration
@EnableWebSecurity
@FieldDefaults(level=PRIVATE)
public class SecurityConfiguration {
	@Value("${crypto.jwt.secret-key}")
	String jwtSecretKey;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.sessionManagement(session -> {
					session.sessionCreationPolicy(STATELESS);
					session.sessionConcurrency((concurrency) -> 
						concurrency.maximumSessions(1)
								   .expiredUrl("/"));

				})
				.authorizeHttpRequests(authorize -> {
					authorize.requestMatchers(POST, "/", "/auth", "/auth/**")
							 .permitAll()    
							 .anyRequest()
							 .access(new WebExpressionAuthorizationManager("hasRole('CUSTOMER')"));
				})
				.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
				.build();
	}
	
	/* WARNING: don't gen keys this way in prod! instead: 
	 * 1. point to secured files or
	 * 2. inject values from a cloud KMS/Vault  
	 * */   
	@Bean
	@Lazy
    public KeyPair ecKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGen = getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
        keyGen.initialize(ecSpec);
        return keyGen.generateKeyPair();
    }
    
    @Bean
    @Lazy
    public ECPublicKey ecPublicKey(KeyPair keyPair) {
        return (ECPublicKey) keyPair.getPublic();
    }

    
    @Bean
    @Lazy
    public ECPrivateKey ecPrivateKey(KeyPair keyPair) {
        return (ECPrivateKey) keyPair.getPrivate();
    }
    
    @Bean
    @Lazy
	JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
		ECKey ecKey = new ECKey.Builder(
                Curve.P_256, 
                (ECPublicKey) keyPair.getPublic()
        )
        .privateKey((ECPrivateKey) keyPair.getPrivate())
        .build();

        return new ImmutableJWKSet<>(new JWKSet(ecKey));
	}
    
    @Bean
    @Lazy
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }
    
    @Bean
    @Lazy
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return NimbusJwtDecoder
                .withJwkSource(jwkSource)
                .jwsAlgorithm(ES256)
                .build();
    }
	
	@Bean
    PasswordEncoder passwordEncoder() {
		return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
	
	@Bean
	@Lazy
	JwtService jwtService(JwtEncoder encoder) {
		return new SpringJwtService(encoder);
	}
	
	@Bean
    @Lazy
    PasswordHasher passwordHasher(PasswordEncoder encoder) {
    	return new SpringPasswordHasher(encoder);
    }
}