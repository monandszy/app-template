package code.modules.conversation;

import code.openApi.ApiClient;
import code.openApi.infrastructure.ModelsApi;
import code.openApi.model.Content;
import code.openApi.model.GenerateContentRequest;
import code.openApi.model.GenerateContentResponse;
import code.openApi.model.ListModelsResponse;
import code.openApi.model.Part;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelApiService {

  private static final ApiClient apiClient = new ApiClient();
  private static final ModelsApi modelsApi = new ModelsApi(apiClient);
  private static final String errorFormat = "1";
  private static final String dataFormat = "json";
  private static final String apiToken = "AIzaSyCgRiOEsvoG7o_lOir7e0PICmPTo4kjuNk";
  private static final String model = "gemini-1.5-flash";
  // String | Selector specifying which fields to include in a partial response.
  private static final String fields = null; // I have no idea how to use this
  private static final Boolean isPrettyPrint = false;
  private static final String userIdentificationQuota = "TEST_USER";

// String | Upload protocol for media (e.g. \"raw\", \"multipart\").
//    String uploadProtocol = "uploadProtocol_example";
// String | Legacy upload protocol for media (e.g. \"media\", \"multipart\").
//    String uploadType = "uploadType_example";

  //  private ObjectMapper objectMapper;
  static {
    apiClient.setBasePath("https://generativelanguage.googleapis.com");
  }

//  @SneakyThrows
//  public void generate() {
  public static void main(String[] args) {
    GenerateContentRequest request = new GenerateContentRequest();
    String query = "write a poem!";
    Part part = new Part();
    part.setText(query);

    Content content = new Content();
    content.setParts(List.of(part));
    List<Content> contentList = List.of(content);

    request.setContents(contentList);
    GenerateContentResponse generateContentResponse = generateContent(request);
    System.out.println(generateContentResponse
      .getCandidates().getFirst().getContent().getParts().getFirst().getText());
  }

  private static GenerateContentResponse generateContent(GenerateContentRequest request) {
    return modelsApi.generativelanguageModelsGenerateContent(
      model,
      errorFormat,
      null,
      dataFormat,
      null,
      fields,
      apiToken,
      null,
      isPrettyPrint,
      userIdentificationQuota,
      null,
      null,
      request
    ).block();
  }

  private static ListModelsResponse getModelList() {
//    ListModelsResponse response = getModelList();
//    List<Model> models = response.getModels();
//    models.stream().map(e -> e.getName()).forEach(System.out::println);
    return modelsApi.generativelanguageModelsList(
      errorFormat,
      null,
      dataFormat,
      null,
      null,
      apiToken,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    ).block();
  }
}