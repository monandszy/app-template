package code.modules.conversation.data;

import code.modules.conversation.data.jpa.ConversationJpaRepo;
import code.modules.conversation.data.jpa.RequestJpaRepo;
import code.modules.conversation.data.jpa.ResponseJpaRepo;
import code.modules.conversation.service.Conversation;
import code.modules.conversation.service.ConversationDao;
import code.modules.conversation.service.Request;
import code.modules.conversation.service.Response;
import code.modules.conversation.util.ConversationMapper;
import code.util.RepositoryAdapter;
import lombok.AllArgsConstructor;

@RepositoryAdapter
@AllArgsConstructor
public class ConversationRepository implements ConversationDao {

  private ConversationJpaRepo conversationJpaRepo;
  private RequestJpaRepo requestJpaRepo;
  private ResponseJpaRepo responseJpaRepo;
  private ConversationMapper mapper;

  public Request create(Request request) {
    RequestEntity entity = mapper.domainToEntity(request);
    entity.setConversation(mapper.domainToEntity(request.getConversation()));
    RequestEntity saved = requestJpaRepo.save(entity);
    return mapper.entityToDomain(saved);
  }

  public Response create(Response response) {
    ResponseEntity entity = mapper.domainToEntity(response);
    entity.setRequest(mapper.domainToEntity(response.getRequest()));
    ResponseEntity saved = responseJpaRepo.save(entity);
    return mapper.entityToDomain(saved);
  }

  public Conversation create(Conversation conversation) { // TODO remember about account id
    ConversationEntity entity = mapper.domainToEntity(conversation);
    ConversationEntity saved = conversationJpaRepo.save(entity);
    return mapper.entityToDomain(saved);
  }
}