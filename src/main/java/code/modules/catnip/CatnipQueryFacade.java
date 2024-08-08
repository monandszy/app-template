package code.modules.catnip;

import code.configuration.Facade;
import code.modules.catnip.data.CatnipDao;
import code.modules.catnip.util.CatnipMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;

@Facade
@AllArgsConstructor
public class CatnipQueryFacade {

  private CatnipDao catnipDao;
  private CatnipMapper catnipMapper;

  public Page<CatnipReadDto> getCatnipPage(@NonNull PageRequest pageRequest) {
    return catnipDao.getPage(pageRequest).map(catnipMapper::domainToReadDto);
  }

}