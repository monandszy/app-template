package code.web;

import code.config.Constants;
import code.modules.catnip.CatnipFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
class CatnipPage {
  private CatnipFacade catnipFacade;
  private final PageRequest defaultRequest = PageRequest.of(1, Constants.PAGE_SIZE, Sort.by("id"));

  @GetMapping("/catnip")
  String getCatnipPage(
      @RequestParam(defaultValue = "1") int page,
      Model model
  ) {
    model.addAttribute("currentPage", catnipFacade.getCatnipPage(defaultRequest.withPage(page)));
    return "catnip";
  }
}