package code.modules.catnip;

import code.configuration.Constants;
import code.configuration.ContextConfig;
import code.configuration.FacadeAbstractIT;
import code.modules.catnip.data.CatnipDao;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ContextConfig.CatnipModuleContext.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CatnipFacadeTest extends FacadeAbstractIT {

  private CatnipFacade catnipFacade;
  private CatnipDao catnipDao;

  @Test
  @Transactional
  void should_return_paginated_catnip() {
    // given
    catnipDao.create(TestFixtures.catnip);
    PageRequest pageRequest = PageRequest.of(0, Constants.PAGE_SIZE);
    // when
    Page<CatnipReadDto> catnipPage = catnipFacade.getCatnipPage(pageRequest);
    // then
    assertThat(catnipPage).isNotNull();
    assertThat(catnipPage.getContent()).isNotEmpty();
    assertThat(catnipPage.getContent().getFirst().id()).isNotNull();
    assertThat(catnipPage.getTotalElements()).isGreaterThan(0);
  }

  @Test
  @Transactional
  void should_create_catnip() {
    //given
    CatnipCreateDto createDto = TestFixtures.catnipCreateDto;
    //when
    CatnipReadDto createdCatnip = catnipFacade.createCatnip(createDto);
    // then
    assertThat(createdCatnip).isNotNull();
    assertThat(createdCatnip.id()).isNotNull();
  }

  private ApplicationContext applicationContext;

  @Test
  public void testBeanInitialization() {
    String[] beanNames = applicationContext.getBeanDefinitionNames();
    System.out.println("Beans initialized in the context:");
    for (String beanName : beanNames) {
      System.out.println(beanName);
    }
  }


}