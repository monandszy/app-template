package code.util;

import static code.modules.catnips.CatnipQueryFacade.CatnipReadDto;

import code.modules.accounts.AccountCommandFacade.AccountCreateDto;
import code.modules.catnips.CatnipCommandFacade.CatnipCreateDto;
import code.modules.catnips.service.Catnip;
import java.util.List;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestFixtures {

  public Catnip catnip = Catnip.builder().build();

  public CatnipReadDto catnipReadDto = new CatnipReadDto(UUID.randomUUID());

  public CatnipCreateDto catnipCreateDto = new CatnipCreateDto();
  public AccountCreateDto accountCreateDto = new AccountCreateDto("email@email.com", "password");
  public static List<AccountCreateDto> invalidAccountCreateDto = List.of(
    new AccountCreateDto(null, "password123"), // Invalid email (null)
    new AccountCreateDto("invalid-email", "pass"), // Invalid email and short password
    new AccountCreateDto("test@example.com", null), // Invalid password (null)
    new AccountCreateDto("test@example.com", ""), // Invalid password (blank)
    new AccountCreateDto("", "password123") // Invalid email (blank)
  );
}