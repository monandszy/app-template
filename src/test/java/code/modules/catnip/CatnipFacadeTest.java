package code.modules.catnip;

import code.config.TestContainersConfig;
import code.modules.catnip.data.CatnipRepo;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@ApplicationModuleTest
//@SpringBootTest(classes = {App.class})
@ActiveProfiles("test")
@Import(TestContainersConfig.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CatnipFacadeTest {

  private CatnipFacade catnipFacade;
  private CatnipRepo catnipRepo;

  @Test
  void should_return_paginated_catnip() {
    // given
    int page = 1;
    // when
//    catnipFacade.getCatnips()
    // then
  }
}