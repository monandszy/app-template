package code.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;

@TestConfiguration
@EnableWebSecurity
public class TestSecurityConfig {

  @Bean
  public UserDetailsService users() {
    UserDetails user = User.builder()
      .username("user")
      .password("{noop}password")
      .roles("USER")
      .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public SecurityFilterChain securityDisabled(HttpSecurity http) throws Exception {
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .requestCache((cache) -> cache
        .requestCache(new NullRequestCache())
      )
      .authorizeHttpRequests(requests -> requests
        .anyRequest().permitAll()
      )
      .build();
  }
}