package code.web.accounts;

import code.modules.accounts.UserQueryFacade;
import code.modules.accounts.UserQueryFacade.LoginRequestDto;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
@AllArgsConstructor
public class AuthenticationController {

  private UserQueryFacade userQueryFacade;

  @GetMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  String login(
    @RequestHeader(value = "HX-Request", required = false) String hxRequest,
    Model model
  ) {
    model.addAttribute("loginRequest", new LoginRequestDto("", ""));
    if (Objects.nonNull(hxRequest)) {
      return "authentication/login :: loginForm";
    } else {
      return "authentication/login";
    }
  }


}