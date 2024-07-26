package code.modules.catnip;

import code.config.Constants;
import code.config.TestContainersConfig;
import code.modules.catnip.data.CatnipRepo;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.util.List;

@ApplicationModuleTest
@ActiveProfiles("test")
@Import(TestContainersConfig.class)
//@TestConstructor(autowireMode = AutowireMode.ALL)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CatnipFacadeTest {

  private CatnipFacade catnipFacade;
  private CatnipRepo catnipRepo;

  @Test
  void should_return_paginated_catnip() {
    // given
    int pageNumber = 1;
    PageRequest pageRequest = PageRequest.of(pageNumber, Constants.PAGE_SIZE, Sort.by("id"));
    // when
    Page<CatnipReadDto> catnipPage = catnipFacade.getCatnipPage(pageRequest);
    // then
  }
}