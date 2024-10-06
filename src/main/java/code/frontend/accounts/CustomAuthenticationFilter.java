package code.frontend.accounts;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
    super.setAuthenticationFailureHandler((request, response, exception) ->
      response.sendRedirect("/login?invalid")
    ); // Setting this in SecurityConfig did not work even tho it initialized correctly
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    // TODO data persisting in form on failure

    log.info("Attempting to authenticate user: [{}], [{}]", email, password);
    // not sus at all
    try {
      Authentication auth = super.attemptAuthentication(request, response);
      log.info("Account [{}] authenticated successfully", email);
      return auth;
    } catch (AuthenticationException e) {
      log.warn("Authentication failed for account: [{}] - [{}]", email, e.getMessage());
      throw e; // rethrow to let Spring handle it
    }
  }

  @Override
  protected void successfulAuthentication(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain,
    Authentication authResult
  ) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    log.info("Account [{}] logged in successfully", authResult.getName());
    response.sendRedirect("/"); // Redirect after successful login
  }

  @Override
  protected void unsuccessfulAuthentication(
    HttpServletRequest request, HttpServletResponse response, AuthenticationException failed
  ) throws IOException, ServletException {
    super.unsuccessfulAuthentication(request, response, failed);
    log.info("Account login failed [{}]", failed.getMessage());
  }
}