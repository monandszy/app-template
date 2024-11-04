package code.modules.googleApi;

import code.modules.googleApi.internal.ApiCallService;
import code.modules.googleApi.internal.ApiModelMapper;
import code.openApi.model.GenerateContentResponse;
import code.openApi.model.ListModelsResponse;
import code.openApi.model.Model;
import code.util.Facade;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Facade
@AllArgsConstructor
public class GoogleApiAdapter {

  private ApiModelMapper apiModelMapper;
  private ApiCallService apiCallService;
  private ObjectMapper objectMapper;

  @SneakyThrows
  public ApiResponseDto generate(ApiRequestDto request) {
    GenerateContentResponse response = apiCallService.modelsGenerateContent(apiModelMapper.dtoToApiModel(request));
    return apiModelMapper.apiModelToDto(response);
  }

  public List<String> getModelList() {
    ListModelsResponse response = apiCallService.modelsList();
    List<Model> models = response.getModels();
    return models.stream().map(Model::getName).toList();
  }

  public record ApiRequestDto(
    @NotBlank
    String text
  ) {}

  public record ApiResponseDto(
    String text
  ) {}
}