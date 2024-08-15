package code.modules.catnip.data;

import code.modules.catnip.service.Catnip;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CatnipDao {

  Page<Catnip> search(PageRequest pageRequest, ExampleMatcher matcher, Catnip probe);

  Page<Catnip> getPage(PageRequest pageRequest);

  Catnip create(Catnip catnip);
}