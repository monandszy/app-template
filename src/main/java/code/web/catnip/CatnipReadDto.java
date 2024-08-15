package code.web.catnip;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record CatnipReadDto(
  @NonNull UUID id
) {
}