package code.modules.catnips;

import code.modules.catnips.service.Catnip;
import code.modules.catnips.service.CatnipDao;
import code.modules.catnips.util.CatnipMapper;
import code.util.Facade;
import java.util.UUID;
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

  // TODO
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

  public record CatnipReadDto(
    @NonNull UUID id
  ) {
  }

}