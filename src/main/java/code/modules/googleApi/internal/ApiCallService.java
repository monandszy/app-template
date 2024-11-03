package code.modules.googleApi.internal;

import code.openApi.infrastructure.ModelsApi;
import code.openApi.model.GenerateContentRequest;
import code.openApi.model.GenerateContentResponse;
import code.openApi.model.ListModelsResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApiCallService {

  private ModelsApi modelsApi;

  private static final String errorFormat = "1";
  private static final String dataFormat = "json";
  private static final String apiToken = Authorization.GoogleApiToken;
  private static final String model = "gemini-1.5-flash";
  // String | Selector specifying which fields to include in a partial response.
  private static final String fields = null; // I have no idea how to use this
  private static final Boolean isPrettyPrint = false;
  private static final String userIdentificationQuota = "TEST_USER";

// String | Upload protocol for media (e.g. \"raw\", \"multipart\").
//    String uploadProtocol = "uploadProtocol_example";
// String | Legacy upload protocol for media (e.g. \"media\", \"multipart\").
//    String uploadType = "uploadType_example";

  public GenerateContentResponse modelsGenerateContent(GenerateContentRequest request) {
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
  public ListModelsResponse modelsList() {
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