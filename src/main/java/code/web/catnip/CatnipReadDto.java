package code.web.catnip;

import java.util.UUID;
import org.springframework.lang.NonNull;

public record CatnipReadDto(
  @NonNull UUID id
) {
}