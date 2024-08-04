package code.modules.catnip.data;

import code.modules.catnip.service.Catnip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CatnipDao {
  Page<Catnip> getPage(PageRequest pageNumber);

  Catnip create(Catnip catnip);
}