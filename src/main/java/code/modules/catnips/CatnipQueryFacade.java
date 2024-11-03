package code.modules.catnips;

import code.modules.catnips.service.Catnip;
import code.modules.catnips.service.CatnipDao;
import code.modules.catnips.util.CatnipMapper;
import code.util.Facade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

@Facade
@Slf4j
@AllArgsConstructor
public class CatnipQueryFacade {

  private CatnipDao catnipDao;
  private CatnipMapper catnipMapper;

  public Page<CatnipReadDto> getPage(
    PageRequest pageRequest
  ) {
    Page<CatnipReadDto> page = catnipDao.getPage(pageRequest)
      .map(catnipMapper::domainToReadDto);
    log.info("Catnip Page: {}", page);
    return page;
  }

  // TODO
  public Page<CatnipReadDto> searchCatnip(
    PageRequest pageRequest,
    String query
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

  public record CatnipReadDto(
    UUID id
  ) {}

}