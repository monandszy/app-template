package code.modules.catnips;

import code.events.CatnipCreatedEvent;
import code.modules.catnips.CatnipQueryFacade.CatnipReadDto;
import code.modules.catnips.data.CatnipDao;
import code.modules.catnips.service.Catnip;
import code.modules.catnips.util.CatnipMapper;
import code.util.Facade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;

@Slf4j
@Facade
@AllArgsConstructor
public class CatnipCommandFacade {

  private CatnipDao catnipDao;
  private CatnipMapper catnipMapper;
  private ApplicationEventPublisher publisher;

  public CatnipReadDto createCatnip(@NonNull CatnipCreateDto createDto) {
    Catnip created = catnipDao.create(catnipMapper.createDtoToDomain(createDto));
    log.info("Created Catnip: {}", created);
    this.publisher.publishEvent(new CatnipCreatedEvent(created.getId()));
    return catnipMapper.domainToReadDto(created);
  }

  public record CatnipCreateDto(

  ) {
  }
}