package code.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
class Home {

  @GetMapping(value = "/")
  String getHome() {
    return "index";
  }

}