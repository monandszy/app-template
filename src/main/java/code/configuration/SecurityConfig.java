package code.configuration;

import code.frontend.accounts.CustomAuthenticationFilter;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authProvider(
    UserDetailsService userDetailsService,
    PasswordEncoder passwordEncoder
  ) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(userDetailsService);
    return provider;
  }

  @Bean
  public AuthenticationManager authManager(
    HttpSecurity http,
    AuthenticationProvider authProvider
  ) throws Exception {
    return http
      .getSharedObject(AuthenticationManagerBuilder.class)
      .authenticationProvider(authProvider)
      .build();
  }

  @Bean
  @SneakyThrows
  @ConditionalOnProperty(value = "spring.security.enabled",
    havingValue = "true", matchIfMissing = true)
  public SecurityFilterChain securityEnabled(
    HttpSecurity http,
    AuthenticationManager auth
  ) {
    return http
//      .csrf(AbstractHttpConfigurer::disable)
      .requestCache((cache) -> cache
        .requestCache(new NullRequestCache())
      )
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(
          "/login",
          "/error",
          "/register",
          "/css/*",
          "/js/*",
          "/vendored/*",
          "favicon.ico"
        ).permitAll()
        .requestMatchers(
          "/catnip",
          "/catnip/*",
          "/"
        ).authenticated()
      )
      .formLogin(authorize -> authorize
        .loginPage("/login")
        .usernameParameter("email")
        .successForwardUrl("/")
        .failureForwardUrl("/login?invalid")
        .permitAll()
      )
      .logout(authorize -> authorize
        .logoutSuccessUrl("/login?logout")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .permitAll()
      )
      .exceptionHandling(exh -> exh
        .authenticationEntryPoint((request, response, authException) -> {
          response.addHeader("message", authException.getMessage());
          response.sendRedirect("/login?unauthorized");
        }))
      .addFilterBefore(new CustomAuthenticationFilter(auth), UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  @Bean
  @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
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