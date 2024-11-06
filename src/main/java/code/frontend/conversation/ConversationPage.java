package code.frontend.conversation;

import code.configuration.Constants;
import code.modules.conversation.ConversationCommandFacade;
import code.modules.conversation.ConversationCommandFacade.ConversationBeginDto;
import code.modules.conversation.ConversationCommandFacade.RequestGenerateDto;
import code.modules.conversation.ConversationQueryFacade;
import static code.modules.conversation.ConversationQueryFacade.ConversationReadDto;
import static code.modules.conversation.ConversationQueryFacade.RequestReadDto;
import java.security.Principal;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@AllArgsConstructor
@RequestMapping("/conversation")
@Slf4j
public class ConversationPage {

  private ConversationQueryFacade queryFacade;
  private ConversationCommandFacade commandFacade;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  String index(
    @RequestHeader(value = "HX-Request", required = false) String hxRequest,
    Principal principal,
    Model model
  ) {
    list(principal, model);
    model.addAttribute("conversationBeginDto", new ConversationBeginDto(null));
    if (Objects.nonNull(hxRequest)) {
      return "conversation/content :: fragment";
    } else {
      return "conversation/content";
    }
  }

  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  String list(Principal principal, Model model) {
    PageRequest pageRequest = PageRequest.of(0, Constants.PAGE_SIZE);
    UUID accountId = UUID.fromString(principal.getName());
    Page<ConversationReadDto> conversationPage = queryFacade.getConversationPage(pageRequest, accountId);
    model.addAttribute("conversationPage", conversationPage);
    return "conversation/sidebar :: fragment";
  }

  @PostMapping
  @ResponseBody
  ResponseEntity<ConversationReadDto> beginConversation(
    @ModelAttribute ConversationBeginDto beginDto,
    Principal principal
  ) {
    beginDto = beginDto.withAccountId(UUID.fromString(principal.getName()));
    ConversationReadDto readDto = commandFacade.begin(beginDto);
    // conversation is created, the generated request filed is null
    return ResponseEntity.status(HttpStatus.CREATED).body(readDto);
  }

  @GetMapping("/{conversationId}")
  @ResponseStatus(HttpStatus.OK)
  String window(
    @RequestHeader(value = "HX-Request", required = false) String hxRequest,
    @PathVariable String conversationId,
    Principal principal,
    Model model
  ) {
    PageRequest pageRequest = PageRequest.of(0, Constants.PAGE_SIZE);
    UUID id = UUID.fromString(conversationId);
    ConversationReadDto readDto = new ConversationReadDto(id);
    Page<RequestReadDto> requestPage = queryFacade.getRequestPage(pageRequest, readDto);
    model.addAttribute("requestPage", requestPage);
    model.addAttribute("requestGenerateDto", new RequestGenerateDto(null, id));
    if (Objects.nonNull(hxRequest)) {
      return "conversation/window :: fragment";
    } else {
      list(principal, model);
      return "conversation/window";
    }
  }

  @GetMapping("/introduction")
  @ResponseStatus(HttpStatus.OK)
  String introduction(
    Model model
  ) {
    // Add the request object to model. if decide to gen must
    model.addAttribute("conversationBeginDto", new ConversationBeginDto(null));
    return "conversation/introduction-window :: fragment";
  }


  @PostMapping("/generate")
  @ResponseStatus(HttpStatus.OK)
  String generate(
    @ModelAttribute RequestGenerateDto generateDto,
    Model model
  ) {
    RequestReadDto readDto = commandFacade.generate(generateDto);
    model.addAttribute("requestReadDto", readDto);
    return "conversation/window :: content-fragment";
  }


}