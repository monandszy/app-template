package code.modules;

import code.configuration.Constants;
import code.configuration.ContextConfig;
import code.configuration.FacadeAbstract;
import code.modules.catnips.CatnipCommandFacade;
import code.modules.catnips.CatnipCommandFacade.CatnipCreateDto;
import code.modules.catnips.CatnipQueryFacade;
import static code.modules.catnips.CatnipQueryFacade.CatnipReadDto;
import code.modules.catnips.data.CatnipEntity;
import code.modules.catnips.data.CatnipJpaRepo;
import code.modules.catnips.service.Catnip;
import code.modules.catnips.service.CatnipDao;
import code.util.TestFixtures;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Import(ContextConfig.CatnipModuleContext.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CatnipFacadeTest extends FacadeAbstract {

  private CatnipQueryFacade catnipQueryFacade;
  private CatnipCommandFacade catnipCommandFacade;
  private CatnipDao catnipDao;
  private CatnipJpaRepo catnipJpaRepo;

  @Test
  @Transactional
  void should_return_paginated_catnip() {
    // given
    catnipDao.create(TestFixtures.catnip);
    PageRequest pageRequest = PageRequest.of(0, Constants.PAGE_SIZE);
    // when
    Page<CatnipReadDto> catnipPage = catnipQueryFacade.getPage(pageRequest);
    // then
    assertThat(catnipPage).isNotNull();
    assertThat(catnipPage.getContent()).isNotEmpty();
    assertThat(catnipPage.getContent().getFirst().id()).isNotNull();
  }

  @Test
  @Transactional
  @Disabled
  void should_return_searched_catnip() {
    // given
    String query = "TODO field value";
    catnipDao.create(TestFixtures.catnip);
    Catnip expected = TestFixtures.catnip;  // .withField(value + xxx)
    catnipDao.create(expected);
    PageRequest pageRequest = PageRequest.of(0, Constants.PAGE_SIZE);
    // when
    Page<CatnipReadDto> catnipPage = catnipQueryFacade.searchCatnip(pageRequest, query);
    // then
    assertThat(catnipPage).isNotNull();
    assertThat(catnipPage.getContent()).isNotEmpty();
    assertThat(catnipPage.getContent().getFirst().id()).isNotNull();
    // TODO field contains query eg. the correct one was returned
  }

  @Test
  @Transactional
  void should_create_catnip() {
    // given
    CatnipCreateDto createDto = TestFixtures.catnipCreateDto;
    // when
    CatnipReadDto createdCatnip = catnipCommandFacade.createCatnip(createDto);
    // then
    assertThat(createdCatnip).isNotNull();
    assertThat(createdCatnip.id()).isNotNull();
    CatnipEntity entity = catnipJpaRepo.findAll().getFirst();
    assertThat(entity).isNotNull();
  }

}