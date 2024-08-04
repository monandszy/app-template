package code.modules.catnip.data;

import code.configuration.RepositoryAdapter;
import code.modules.catnip.service.Catnip;
import code.modules.catnip.util.CatnipMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RepositoryAdapter
@AllArgsConstructor
public class CatnipRepo implements CatnipDao {

  private CatnipJpaRepo catnipJpaRepo;
  private CatnipMapper catnipMapper;

  @Override
  public Page<Catnip> getPage(PageRequest pageRequest) {
    Page<CatnipEntity> page = catnipJpaRepo.findAll(pageRequest);
    return page.map(catnipMapper::entityToDomain);
  }

  @Override
  public Catnip create(Catnip catnip) {
    CatnipEntity entity = catnipMapper.domainToEntity(catnip);
    return catnipMapper.entityToDomain(catnipJpaRepo.save(entity));
  }
}