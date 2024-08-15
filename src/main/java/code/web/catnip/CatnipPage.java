package code.web.catnip;

import code.configuration.Constants;
import code.modules.catnip.CatnipCommandFacade;
import code.modules.catnip.CatnipQueryFacade;
import code.util.ControllerUtil;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static code.util.ControllerUtil.getOrSetSessionAttr;
import static code.util.SessionAttr.currentPage;
import static code.util.SessionAttr.currentQuery;
import static code.util.SessionAttr.currentSort;

@Controller
@AllArgsConstructor
@RequestMapping("/catnip")
@Slf4j
public class CatnipPage implements ControllerUtil {
  private CatnipQueryFacade catnipQueryFacade;
  private CatnipCommandFacade catnipCommandFacade;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  String index(Model model, HttpSession session) {
    model.addAttribute("catnipCreateDto", new CatnipCreateDto());
    list(null, null, null, model, session); // adds initial data to model

    List<String> sortOptions = List.of();
    model.addAttribute("sortOptions", sortOptions);
    return "catnip";
  }

  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  String list(
    @RequestParam(required = false) Integer page,
    @RequestParam(required = false) String sort,
    @RequestParam(required = false) String query,
    Model model,
    HttpSession session
  ) {
    page = getOrSetSessionAttr(session, currentPage, page, 0);
    sort = getOrSetSessionAttr(session, currentSort, sort, "id");
    query = getOrSetSessionAttr(session, currentQuery, query, "");

    PageRequest pageRequest = PageRequest.of(page, Constants.PAGE_SIZE, Sort.by(sort));
    Page<CatnipReadDto> catnipPage;
    if (query.isBlank())
      catnipPage = catnipQueryFacade.requestCatnipPage(pageRequest);
    else
      catnipPage = catnipQueryFacade.searchCatnip(pageRequest, query);

    model.addAttribute("newPage", catnipPage);

    PaginationRangeDto range = getPaginationRange(page, catnipPage.getTotalPages(), Constants.RANGE_SIZE, Constants.RANGE_HALF);
    model.addAttribute("paginationRange", range);
    return "fragments/catnip-list :: catnipList";
  }

  public PaginationRangeDto getPaginationRange(int page, int totalPages, int rangeSize, int half) {
    int rangeStart, rangeEnd;

    if (totalPages <= rangeSize) {
      rangeStart = 0;
      rangeEnd = totalPages - 1;
    } else {
      if (page <= half) {
        rangeStart = 0;
        rangeEnd = rangeSize - 1;
      } else if (page >= (totalPages - half)) {
        rangeStart = totalPages - rangeSize;
        rangeEnd = totalPages - 1;
      } else {
        rangeStart = page - half;
        rangeEnd = page + half;
      }
    }
    log.info("PageRange {} - {}", rangeStart, rangeEnd);
    return new PaginationRangeDto(rangeStart, rangeEnd);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  void create(CatnipCreateDto createDto) {
    catnipCommandFacade.createCatnip(createDto);
  }
}