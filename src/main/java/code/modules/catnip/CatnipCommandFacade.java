package code.modules.catnip;

import code.modules.catnip.data.CatnipDao;
import code.modules.catnip.service.Catnip;
import code.modules.catnip.util.CatnipMapper;
import code.util.Facade;
import code.web.catnip.CatnipCreateDto;
import code.web.catnip.CatnipReadDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

@Slf4j
@Facade
@AllArgsConstructor
public class CatnipCommandFacade {

  private CatnipDao catnipDao;
  private CatnipMapper catnipMapper;

  public CatnipReadDto createCatnip(@NonNull CatnipCreateDto createDto) {
    log.info("Created Catnip: {}", createDto);
    Catnip created = catnipDao.create(catnipMapper.createDtoToDomain(createDto));
    log.info("Created Catnip: {}", created);
    return catnipMapper.domainToReadDto(created);
  }
}