package code.modules.conversation;

import code.modules.conversation.ConversationsQueryFacade.ConversationReadDto;
import code.modules.conversation.ConversationsQueryFacade.ResponseReadDto;
import code.modules.conversation.service.Conversation;
import code.modules.conversation.service.ConversationDao;
import code.modules.conversation.service.Request;
import code.modules.conversation.service.Response;
import code.modules.conversation.util.ConversationMapper;
import code.modules.googleApi.GoogleApiAdapter;
import code.modules.googleApi.GoogleApiAdapter.ApiRequestDto;
import code.modules.googleApi.GoogleApiAdapter.ApiResponseDto;
import code.util.Facade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Facade
@AllArgsConstructor
public class ConversationCommandFacade {

  private GoogleApiAdapter googleApiAdapter;
  private ConversationMapper mapper;
  private ConversationDao conversationDao;

  public ResponseReadDto generate(@Valid RequestGenerateDto requestDto) {
    // call to other module facade, a dependency
    ApiRequestDto apiRequest = new ApiRequestDto(requestDto.text());
    ApiResponseDto apiResponse = googleApiAdapter.generate(apiRequest);

    Conversation conversation = Conversation.builder().id(requestDto.conversationId()).build();
    Request request = mapper.createDtoToDomain(requestDto);
    request = conversationDao.create(request.withConversation(conversation));
    Response response = Response.builder()
      .text(apiResponse.text()).request(request).build();
    response = conversationDao.create(response);
    return mapper.domainToReadDto(response);
  }

  public ConversationReadDto begin(ConversationBeginDto conversationDto) {
    Conversation conversation = mapper.createDtoToDomain(conversationDto);
    conversation = conversationDao.create(conversation);
    return mapper.domainToReadDto(conversation);
  }

  public record ConversationBeginDto(
    @NotNull
    UUID accountId
  ) {}

  public record RequestGenerateDto(
    @NotBlank
    String text,
    @NotNull
    UUID conversationId
  ) {}
}