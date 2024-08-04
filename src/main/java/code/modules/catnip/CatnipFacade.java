package code.modules.catnip;

import code.configuration.Facade;
import code.modules.catnip.data.CatnipDao;
import code.modules.catnip.service.Catnip;
import code.modules.catnip.util.CatnipMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;

@Facade
@AllArgsConstructor
public class CatnipFacade {

  private CatnipDao catnipDao;
  private CatnipMapper catnipMapper;

  public Page<CatnipReadDto> getCatnipPage(@NonNull PageRequest pageRequest) {
    return catnipDao.getPage(pageRequest).map(catnipMapper::domainToReadDto);
  }

  public CatnipReadDto createCatnip(@NonNull CatnipCreateDto createDto) {
    Catnip created = catnipDao.create(catnipMapper.createDtoToDomain(createDto));
    return catnipMapper.domainToReadDto(created);
  }
}