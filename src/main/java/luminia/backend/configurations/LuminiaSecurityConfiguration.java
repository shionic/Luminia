package luminia.backend.configurations;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.proc.SingleKeyJWSKeySelector;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import luminia.backend.utils.JwkUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class LuminiaSecurityConfiguration {
    @Bean
    public SecurityFilterChain configureSecurity(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter((jwt) -> {
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) jwt.getClaims().get("roles");
            if (roles == null || roles.isEmpty()) {
                return new ArrayList<>();
            }
            List<GrantedAuthority> list = new ArrayList<>();
            for (String role : roles) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
                list.add(simpleGrantedAuthority);
            }
            return list;
        });

        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated())
                .cors((cors) -> {
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    CorsConfiguration config = new CorsConfiguration();

                    config.setAllowCredentials(true);

                    // Указываем список адресов для которых разрешены кросс-доменные запросы
                    config.addAllowedOrigin("http://127.0.0.1:5000,http://localhost:5000");
                    config.addAllowedHeader(CorsConfiguration.ALL);
                    config.addExposedHeader(CorsConfiguration.ALL);
                    config.addAllowedMethod(CorsConfiguration.ALL);

                    source.registerCorsConfiguration("/**", config);
                    cors.configurationSource(source);
                })
                .oauth2ResourceServer(resourceServerConfigurer -> {
                    resourceServerConfigurer.jwt((jwtConfigurer -> {
                        jwtConfigurer.decoder(jwtDecoder);
                        jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter);
                    }));
                });

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JwtConfigurationProperties properties) throws Exception {
        DefaultJWTProcessor<SecurityContext> processor = new DefaultJWTProcessor<>();
        var publicKey = JwkUtils.readECPublicKey(Path.of(properties.getPublicKeyPath()));
        JWSKeySelector<SecurityContext> jwsKeySelector = new SingleKeyJWSKeySelector<>(JWSAlgorithm.ES256, publicKey);
        processor.setJWSKeySelector(jwsKeySelector);
        return new NimbusJwtDecoder(processor);
    }
}
