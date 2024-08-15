package code.modules.catnip;

import code.modules.catnip.data.CatnipDao;
import code.modules.catnip.service.Catnip;
import code.modules.catnip.util.CatnipMapper;
import code.util.Facade;
import code.web.catnip.CatnipReadDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;

@Facade
@Slf4j
@AllArgsConstructor
public class CatnipQueryFacade {

  private CatnipDao catnipDao;
  private CatnipMapper catnipMapper;

  public Page<CatnipReadDto> requestCatnipPage(
    @NonNull PageRequest pageRequest
  ) {
    Page<CatnipReadDto> page = catnipDao.getPage(pageRequest)
      .map(catnipMapper::domainToReadDto);
    log.info("Catnip Page: {}", page);
    return page;
  }

  public Page<CatnipReadDto> searchCatnip(
    @NonNull PageRequest pageRequest,
    @NonNull String query
  ) {
    ExampleMatcher matcher = ExampleMatcher.matchingAny();
//        .withMatcher("id", match -> match.contains().ignoreCase());
    Catnip probe = Catnip.builder().build();
    Page<CatnipReadDto> page = catnipDao.search(
      pageRequest,
      matcher,
      probe
    ).map(catnipMapper::domainToReadDto);
    log.info("Catnip Search: {}", page);
    return page;
  }

}