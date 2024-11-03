package code.modules;

import code.configuration.ContextConfig;
import code.configuration.FacadeAbstract;
import code.configuration.UtilBeanConfig;
import code.modules.conversation.ConversationCommandFacade;
import code.modules.conversation.ConversationCommandFacade.ConversationBeginDto;
import code.modules.conversation.ConversationCommandFacade.RequestGenerateDto;
import code.modules.conversation.ConversationsQueryFacade;
import code.modules.conversation.ConversationsQueryFacade.ConversationReadDto;
import code.modules.conversation.ConversationsQueryFacade.RequestReadDto;
import static code.modules.conversation.ConversationsQueryFacade.ResponseReadDto;
import code.modules.conversation.data.ConversationEntity;
import code.modules.conversation.data.ConversationRepository;
import code.modules.conversation.data.RequestEntity;
import code.modules.conversation.data.ResponseEntity;
import code.modules.conversation.data.jpa.ConversationJpaRepo;
import code.modules.conversation.data.jpa.RequestJpaRepo;
import code.modules.conversation.data.jpa.ResponseJpaRepo;
import code.modules.conversation.service.Conversation;
import code.modules.googleApi.GoogleApiAdapter;
import code.modules.googleApi.GoogleApiAdapter.ApiRequestDto;
import static code.modules.googleApi.GoogleApiAdapter.ApiResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Slf4j
@Import({ContextConfig.ConversationsModuleContext.class, UtilBeanConfig.class})
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ConversationsFacadeTest extends FacadeAbstract {

  private ConversationsQueryFacade queryFacade;
  private ConversationCommandFacade commandFacade;

  private ConversationRepository repository;
  private ConversationJpaRepo conversationJpaRepo;
  private RequestJpaRepo requestJpaRepo;
  private ResponseJpaRepo responseJpaRepo;

  @MockBean
  private GoogleApiAdapter googleApiAdapter;

  @Test
  void should_save_generated() {
    // given
    Conversation conversation = repository.create(Conversation.builder().build());
    String requestText = "request";
    RequestGenerateDto requestDto = new RequestGenerateDto(requestText, conversation.getId());
    ApiRequestDto apiRequestDto = new ApiRequestDto(requestText);
    String generatedText = "response";
    ApiResponseDto apiResponseDto = new ApiResponseDto(generatedText);
    // when
    Mockito.when(googleApiAdapter.generate(apiRequestDto)).thenReturn(apiResponseDto);
    ResponseReadDto responseDto = commandFacade.generate(requestDto);
    // then
    RequestEntity request = requestJpaRepo.findAll().getFirst();
    ResponseEntity response = responseJpaRepo.findAll().getFirst();
    Mockito.verify(googleApiAdapter).generate(apiRequestDto);
    Assertions.assertEquals(generatedText, responseDto.text());
    Assertions.assertEquals(request.getText(), requestText);
    Assertions.assertEquals(response.getText(), generatedText);
    Assertions.assertEquals(response.getText(), responseDto.text());
  }

  @Test
  void should_begin_conversation() {
    // given
    ConversationBeginDto createDto = new ConversationBeginDto(null);
    // when
    ConversationReadDto readDto = commandFacade.begin(createDto);
    // then
    assertThat(readDto).isNotNull();
    ConversationEntity entity = conversationJpaRepo.findAll().getFirst();
    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isNotNull();
  }

  @Test
  @Disabled
  void should_return_conversation_page() {
    // given
    PageRequest pageRequest;
    // when // based on user, User uuid
    Page<ConversationReadDto> conversationPage = queryFacade.getConversationPage();

    // then
  }

  @Test
  @Disabled
  void should_return_request_page() {
    // given
    ConversationReadDto conversationReadDto = null;
    PageRequest pageRequest;
    // when
    Page<RequestReadDto> queryPage = queryFacade.getQueryPage(conversationReadDto);
    // then
  }
}