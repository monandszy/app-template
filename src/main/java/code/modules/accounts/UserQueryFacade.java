package code.modules.accounts;

import code.util.Facade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Facade
@AllArgsConstructor
@Slf4j
public class UserQueryFacade {

  private AuthenticationManager authenticationManager;

  public void authenticate(LoginRequestDto loginRequest) {
    Authentication authenticationRequest = UsernamePasswordAuthenticationToken
      .unauthenticated(loginRequest.email(), loginRequest.password());
    Authentication authenticationResponse = authenticationManager
      .authenticate(authenticationRequest);
    log.info("Authentication successful: {}", authenticationResponse);
  }

  public record LoginRequestDto(String email, String password) {
  }
}