package code.modules.accounts;

import code.modules.accounts.service.Account;
import code.modules.accounts.service.AccountDao;
import code.modules.accounts.util.AccountMapper;
import code.util.Facade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Facade
@Slf4j
@AllArgsConstructor
public class UserCommandFacade {

  private AccountDao accountDao;
  private PasswordEncoder passwordEncoder;
  private AccountMapper accountMapper;
  private AuthenticationManager authenticationManager;

  public SecurityContext register(@NonNull AccountCreateDto accountDto) {
    log.info("Registering account [{}]", accountDto); // also a bit sus
    Account account = accountDao.create(accountMapper.createDtoToDomain(accountDto)
      .withEmail(accountDto.email())
      .withPassword(passwordEncoder.encode(accountDto.password()))
      .withEnabled(true));
    log.info("Registered account [{}]", account);
    Authentication authRequest = UsernamePasswordAuthenticationToken
      .unauthenticated(accountDto.email(), accountDto.password());
    Authentication authResult = authenticationManager.authenticate(authRequest);
    log.info("Account logged in successfully - [{}]", authResult);
    SecurityContext context = SecurityContextHolder.getContext();
    context.setAuthentication(authResult);
    return context;
  }

  public record AccountCreateDto(@NonNull String email, @NonNull String password) {
  }
}