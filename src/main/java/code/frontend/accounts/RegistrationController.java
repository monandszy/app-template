package code.frontend.accounts;

import static code.modules.accounts.UserCommandFacade.AccountCreateDto;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import code.modules.accounts.UserCommandFacade;
import jakarta.servlet.http.HttpSession;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
public class RegistrationController {

  private UserCommandFacade userCommandFacade;

  @GetMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  String register(
    @RequestHeader(value = "HX-Request", required = false) String hxRequest,
    Model model
  ) {
    model.addAttribute("accountCreateDto", new AccountCreateDto("", ""));
    if (Objects.nonNull(hxRequest)) {
      return "authentication/register :: content";
    } else {
      return "authentication/register";
    }
  }

  @PostMapping("/register")
  RedirectView registerAccount(
    @ModelAttribute("account") AccountCreateDto account,
    HttpSession session
  ) {
    SecurityContext context = userCommandFacade.register(account);
    session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
    return new RedirectView("/");
  }

}