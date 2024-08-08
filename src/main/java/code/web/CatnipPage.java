package code.web;

import code.configuration.Constants;
import code.modules.catnip.CatnipQueryFacade;
import code.modules.catnip.CatnipReadDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/catnip")
@Slf4j
class CatnipPage {
  private CatnipQueryFacade catnipQueryFacade;

  @GetMapping
  String index() {
    return "catnip";
  }

  @GetMapping("/list")
  String list(
    @RequestParam(defaultValue = "0") int page,
    Model model
  ) {
    PageRequest pageRequest = PageRequest.of(page, Constants.PAGE_SIZE, Sort.by("id"));
    Page<CatnipReadDto> catnipPage = catnipQueryFacade.getCatnipPage(pageRequest);
    model.addAttribute("currentPage", catnipPage);
    return "fragments/catnip-list :: list";
  }

  @PostMapping("/click")
  String handleClick(Model model) {
    model.addAttribute("message", "Button clicked!");
    return "fragments/result :: resultContent"; // Thymeleaf fragment
  }
}