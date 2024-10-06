package code.web.accounts;

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
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    log.info("Attempting to authenticate user: [{}], [{}]", email, password);

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
//    response.sendRedirect("/home"); // Redirect after successful login
  }
}