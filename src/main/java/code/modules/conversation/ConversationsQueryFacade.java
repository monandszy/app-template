package code.modules.conversation;

import code.util.Facade;
import org.springframework.data.domain.Page;

@Facade
public class ConversationsQueryFacade {

  public Page<ConversationReadDto> getConversationPage() {
    return null;
  }

  public Page<QueryReadDto> getQueryPage(ConversationReadDto conversationReadDto) {
    return null;
  }

  public record QueryReadDto() {
  }

  public record ConversationReadDto() {
  }

  public record ResponseReadDto() {
  }
}