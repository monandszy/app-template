package code.web.accounts;

import code.modules.accounts.UserQueryFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
class LoginRest {

  private UserQueryFacade userQueryFacade;

//  @PostMapping("/login")
//  @ResponseStatus(HttpStatus.ACCEPTED)
//  void login(@RequestBody UserQueryFacade.LoginRequestDto loginRequest) {
//    log.info("TRUE");
//    userQueryFacade.authenticate(loginRequest);
//  }
}