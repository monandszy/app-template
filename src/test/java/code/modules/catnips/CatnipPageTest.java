package code.modules.catnips;

import static code.modules.catnips.CatnipCommandFacade.CatnipCreateDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import code.configuration.Constants;
import code.frontend.catnips.CatnipPage;
import code.frontend.catnips.CatnipPage.PaginationRangeDto;
import code.modules.catnips.CatnipQueryFacade.CatnipReadDto;
import code.util.TestFixtures;
import java.util.List;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CatnipPage.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CatnipPageTest {

  @MockBean
  private CatnipQueryFacade catnipQueryFacade;
  @MockBean
  private CatnipCommandFacade catnipCommandFacade;

  private CatnipPage catnipPage;

  private MockMvc mockMvc;

  @Test
  void should_return_index() throws Exception {
    Page<CatnipReadDto> mocked = new PageImpl<>(List.of(TestFixtures.catnipReadDto));
    PageRequest expected = PageRequest.of(0, Constants.PAGE_SIZE, Sort.by("id"));
    Mockito.when(catnipQueryFacade.requestCatnipPage(expected)).thenReturn(mocked);
    mockMvc.perform(get("/catnip"))
      .andExpect(model().attribute("newPage", mocked))
      .andExpect(model().attribute("catnipCreateDto", new CatnipCreateDto()))
      .andExpect(view().name("catnip/catnip"));
  }

  @Test
  void should_return_catnip_page() throws Exception {
    String page = "1";
    String sort = "field";
    Page<CatnipReadDto> mocked = new PageImpl<>(List.of(TestFixtures.catnipReadDto));
    PageRequest expected = PageRequest.of(1, Constants.PAGE_SIZE, Sort.by(sort));
    Mockito.when(catnipQueryFacade.requestCatnipPage(expected)).thenReturn(mocked);
    mockMvc.perform(get("/catnip/list")
        .param("page", page).param("sort", sort))
      .andExpect(model().attribute("newPage", mocked))
      .andExpect(view().name("catnip/catnip-list :: catnipList"));
    Mockito.verify(catnipQueryFacade).requestCatnipPage(expected);
  }

  @Test
  void should_search_catnip() throws Exception {
    String query = "text";
    Page<CatnipReadDto> mocked = new PageImpl<>(List.of(TestFixtures.catnipReadDto));
    PageRequest expected = PageRequest.of(0, Constants.PAGE_SIZE, Sort.by("id"));
    Mockito.when(catnipQueryFacade.searchCatnip(expected, query)).thenReturn(mocked);
    mockMvc.perform(get("/catnip/list")
        .param("query", query))
      .andExpect(model().attribute("newPage", mocked))
      .andExpect(view().name("catnip/catnip-list :: catnipList"));
    Mockito.verify(catnipQueryFacade).searchCatnip(expected, query);
  }

  @Test
  void should_fail_on_invalid_page() throws Exception {
    String invalidPage = "-1";
    Page<CatnipReadDto> mocked = new PageImpl<>(List.of(TestFixtures.catnipReadDto));
    mockMvc.perform(get("/catnip/list").param("page", invalidPage))
      .andExpect(status().isBadRequest());
  }

  @Test
  void should_create_catnip() throws Exception {
    CatnipCreateDto expected = TestFixtures.catnipCreateDto;
    mockMvc.perform(post("/catnip"))
      .andExpect(status().isCreated());
    Mockito.verify(catnipCommandFacade).createCatnip(expected);
  }

  @Test
  void should_calculate_pagination_range() {
    int rangeSize = 5;
    int rangeHalf = 2;

    // Less than range
    int totalPages1 = 3;
    int page1 = 1;
    PaginationRangeDto result1 = catnipPage
      .getPaginationRange(page1, totalPages1, rangeSize, rangeHalf);
    Assertions.assertEquals(0, result1.start());
    Assertions.assertEquals(2, result1.end());

    // Start of range
    int totalPages2 = 10;
    int page2 = 1;
    PaginationRangeDto result2 = catnipPage
      .getPaginationRange(page2, totalPages2, rangeSize, rangeHalf);
    Assertions.assertEquals(0, result2.start());
    Assertions.assertEquals(4, result2.end());

    // Middle of range
    int totalPages3 = 10;
    int page3 = 5;
    PaginationRangeDto result3 = catnipPage
      .getPaginationRange(page3, totalPages3, rangeSize, rangeHalf);
    Assertions.assertEquals(3, result3.start());
    Assertions.assertEquals(7, result3.end());

    // End of range
    int totalPages4 = 7;
    int page4 = 6;
    PaginationRangeDto result4 = catnipPage
      .getPaginationRange(page4, totalPages4, rangeSize, rangeHalf);

    Assertions.assertEquals(2, result4.start());
    Assertions.assertEquals(6, result4.end());
  }
}