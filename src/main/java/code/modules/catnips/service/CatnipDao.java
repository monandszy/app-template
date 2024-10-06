package code.modules.catnips.service;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CatnipDao {

  Page<Catnip> search(PageRequest pageRequest, ExampleMatcher matcher, Catnip probe);

  Page<Catnip> getPage(PageRequest pageRequest);

  Catnip create(Catnip catnip);
}