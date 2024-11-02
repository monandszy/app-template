package code.modules;

import static code.modules.accounts.AccountCommandFacade.AccountCreateDto;
import static code.modules.accounts.AccountCommandFacade.AccountReadDto;
import static org.junit.jupiter.api.Assertions.assertThrows;

import code.configuration.ContextConfig;
import code.configuration.FacadeAbstract;
import code.configuration.SecurityConfig;
import code.modules.accounts.AccountCommandFacade;
import code.modules.accounts.service.domain.AuthorityName;
import code.util.TestFixtures;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Import({ContextConfig.AccountModuleContext.class, SecurityConfig.class})
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AccountFacadeTest extends FacadeAbstract {

  private AccountCommandFacade commandFacade;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  void should_register_account() {
    //given
    AccountCreateDto accountCreateDto = TestFixtures.accountCreateDto;
    //when
    AccountReadDto readDto = commandFacade.register(accountCreateDto);
    //then
    Assertions.assertNotNull(readDto);
    Assertions.assertTrue(readDto.enabled());
    Assertions.assertEquals(readDto.email(), accountCreateDto.email());
    Assertions.assertTrue(passwordEncoder.matches(accountCreateDto.password(), readDto.password()));
    Assertions.assertTrue(readDto.authorities().stream()
      .anyMatch(e -> e.equals(AuthorityName.ROLE_USER.name())));
  }

  @Test
  void should_validate_input_data() {
    for (AccountCreateDto accountCreateDto : TestFixtures.invalidAccountCreateDto) {
      assertThrows(ConstraintViolationException.class, () -> {
        commandFacade.register(accountCreateDto);
      }, "Expected an IllegalArgumentException for account: " + accountCreateDto);
    }
  }
}