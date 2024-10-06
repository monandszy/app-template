package code.web.accounts;

import static code.modules.accounts.UserCommandFacade.AccountCreateDto;

import code.modules.accounts.UserCommandFacade;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@AllArgsConstructor
public class RegistrationController {

  private UserCommandFacade userCommandFacade;

  @GetMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  String register(
    @RequestHeader(value = "HX-Request", required = false) String hxRequest
  ) {
    if (Objects.nonNull(hxRequest)) {
      return "authentication/register :: registerForm";
    } else {
      return "authentication/register";
    }
  }

  @PostMapping("/register")
  public String registerAccount(
    @ModelAttribute("account") AccountCreateDto account
  ) {
    userCommandFacade.register(account);
    return "authentication/login";
  }

}