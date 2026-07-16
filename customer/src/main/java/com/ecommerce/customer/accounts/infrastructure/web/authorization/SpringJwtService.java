package com.ecommerce.customer.accounts.infrastructure.web.authorization;

import static java.util.List.of;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.security.oauth2.jwt.JwtClaimsSet.builder;
import static org.springframework.security.oauth2.jwt.JwtEncoderParameters.from;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import com.ecommerce.customer.accounts.usecases.login.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
public class SpringJwtService implements JwtService {
	static String BEARER = "Bearer ";
	JwtEncoder encoder;
	
	@Override
	public String generate(UUID accountId, String accountEmail) {
		Instant now = Instant.now();
        short expiresInSeconds = 300; // five minutes
        
        JwsHeader header = JwsHeader
        		.with(SignatureAlgorithm.ES256)
        		.type("application/json")
        		.build();
        		
        JwtClaimsSet claims = builder()
                .issuer("ecommerce-customer-api")
                .subject(accountId.toString())
                .audience(of("customers"))
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresInSeconds))
                .claim("email", accountEmail)
                .build();

        final String token = encoder
        		.encode(from(header, claims))
        		.getTokenValue();
		
        return BEARER.concat(token);
	}
}
