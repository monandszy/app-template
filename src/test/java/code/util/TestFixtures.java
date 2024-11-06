package code.util;

import code.frontend.account.AuthenticationController.LoginRequestDto;
import code.modules.accounts.AccountCommandFacade.AccountCreateDto;
import code.modules.accounts.service.domain.AuthorityName;
import code.modules.catnips.CatnipCommandFacade.CatnipCreateDto;
import static code.modules.catnips.CatnipQueryFacade.CatnipReadDto;
import code.modules.catnips.service.Catnip;
import java.util.List;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class TestFixtures {

  public Catnip catnip = Catnip.builder().build();

  public CatnipReadDto catnipReadDto = new CatnipReadDto(UUID.randomUUID());

  public CatnipCreateDto catnipCreateDto = new CatnipCreateDto();
  public AccountCreateDto accountCreateDto = new AccountCreateDto("email@email.com", "password");
  public List<AccountCreateDto> invalidAccountCreateDto = List.of(
    new AccountCreateDto(null, "password123"), // Invalid email (null)
    new AccountCreateDto("invalid-email", "pass"), // Invalid email and short password
    new AccountCreateDto("test@example.com", null), // Invalid password (null)
    new AccountCreateDto("test@example.com", ""), // Invalid password (blank)
    new AccountCreateDto("", "password123") // Invalid email (blank)
  );
  public LoginRequestDto loginRequestDto = new LoginRequestDto("email@email.com", "password");
  public UserDetails user = new User(
    "email@email.com",
      "$2a$10$oyzMxbxAJfeQs8txIeg7yeapbS1yglbM71LKrAwjF3lt9rXvkw5A6",
        true,
        true,
        true,
        true,
        List.of(new SimpleGrantedAuthority(AuthorityName.ROLE_USER.name())
    ));
}