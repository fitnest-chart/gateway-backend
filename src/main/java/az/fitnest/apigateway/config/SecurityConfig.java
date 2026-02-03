package az.fitnest.apigateway.config;

import org.springframework.context.annotation.Configuration;

/**
 * Placeholder security configuration.
 *
 * All security is currently handled via gateway filters
 * (see {@link az.fitnest.apigateway.config.AuthFilterConfig}),
 * so we don't register any additional Spring Security beans here.
 */
@Configuration
public class SecurityConfig {
}
