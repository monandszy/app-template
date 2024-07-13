package code.modules.catnip;

import code.modules.catnip.data.CatnipDao;
import code.modules.catnip.service.Catnip;
import code.modules.catnip.util.CatnipMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CatnipFacade {

  private CatnipDao catnipDao;
  private CatnipMapper catnipMapper;

  public Page<CatnipReadDto> getCatnipPage(PageRequest pageRequest) {
    return catnipDao.getPage(pageRequest).map(e -> catnipMapper.modelToReadDto(e));
  }
}