package code.modules.conversation;

import code.modules.conversation.ConversationsQueryFacade.ResponseReadDto;
import code.util.Facade;

@Facade
public class ConversationCommandFacade {

  public ResponseReadDto query(QueryCreateDto queryDto) {
    return null;
  }

  public record QueryCreateDto() {
  }
}