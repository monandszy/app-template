package code.modules;

import static code.modules.accounts.AccountCommandFacade.AccountCreateDto;
import static code.modules.accounts.AccountCommandFacade.AccountReadDto;

import code.configuration.ContextConfig;
import code.configuration.FacadeAbstract;
import code.modules.accounts.AccountCommandFacade;
import code.modules.accounts.service.AuthorityName;
import code.util.TestFixtures;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Import(ContextConfig.AccountModuleContext.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AccountFacadeTest extends FacadeAbstract {

  private AccountCommandFacade commandFacade;
  private PasswordEncoder passwordEncoder;

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
      .anyMatch(e -> e.equals(AuthorityName.ROLE_USER.name()))); // TODO check behaviour, adjust cascade
  }
}