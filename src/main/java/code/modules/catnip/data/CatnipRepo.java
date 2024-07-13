package code.modules.catnip.data;

import code.modules.catnip.service.Catnip;
import code.modules.catnip.util.CatnipMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import static code.config.Constants.PAGE_SIZE;

@Repository
@AllArgsConstructor
public class CatnipRepo implements CatnipDao {

  private CatnipJpaRepo catnipJpaRepo;
  private CatnipMapper catnipMapper;

  @Override
  public Page<Catnip> getPage(PageRequest pageRequest) {
    Page<CatnipEntity> page = catnipJpaRepo.findAll(pageRequest);
    return page.map(e -> catnipMapper.entityToModel(e));
  }
}