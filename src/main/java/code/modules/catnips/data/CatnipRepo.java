package code.modules.catnips.data;

import code.modules.catnips.service.Catnip;
import code.modules.catnips.service.CatnipDao;
import code.modules.catnips.util.CatnipMapper;
import code.util.RepositoryAdapter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RepositoryAdapter
@AllArgsConstructor
public class CatnipRepo implements CatnipDao {

  private CatnipJpaRepo catnipJpaRepo;
  private CatnipMapper catnipMapper;

  @Override
  public Page<Catnip> search(PageRequest pageRequest, ExampleMatcher matcher, Catnip probe) {
    Example<CatnipEntity> example = Example.of(catnipMapper.domainToEntity(probe), matcher);
    Page<CatnipEntity> page = catnipJpaRepo.findAll(example, pageRequest);
    return page.map(catnipMapper::entityToDomain);
  }

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