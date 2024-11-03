package code.modules.conversation.service;

public interface ConversationDao {

  Request create(Request request);

  Response create(Response response);

  Conversation create(Conversation conversation);
}