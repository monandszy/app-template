package code.modules;

import code.configuration.ContextConfig;
import code.configuration.FacadeAbstract;
import code.configuration.UtilBeanConfig;
import code.modules.conversation.ConversationCommandFacade;
import code.modules.conversation.ConversationCommandFacade.QueryCreateDto;
import code.modules.conversation.ConversationsQueryFacade;
import code.modules.conversation.ConversationsQueryFacade.ConversationReadDto;
import code.modules.conversation.ConversationsQueryFacade.QueryReadDto;
import code.modules.conversation.ConversationsQueryFacade.ResponseReadDto;
import code.modules.conversation.ModelApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Slf4j
@Import({ContextConfig.ConversationsModuleContext.class, UtilBeanConfig.class})
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ConversationsFacadeTest extends FacadeAbstract {

  private ConversationsQueryFacade queryFacade;
  private ConversationCommandFacade commandFacade;
  private ModelApiService modelApiService;

  @Test
  void test() {
    ModelApiService.main(null);
  }

  @Test
  @Disabled
  void should_query_and_respond() {
    // given
    QueryCreateDto queryDto = new QueryCreateDto();
    // when
    ResponseReadDto responseDto = commandFacade.query(queryDto);
    // then
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
  void should_return_query_page() {
    // given
    ConversationReadDto conversationReadDto = null;
    PageRequest pageRequest;
    // when
    Page<QueryReadDto> queryPage = queryFacade.getQueryPage(conversationReadDto);
    // then
  }
}