package code.events;

import code.configuration.AmqpIntegrationConfig;
import java.util.UUID;
import org.springframework.modulith.events.Externalized;

@Externalized(target = AmqpIntegrationConfig.Q)
public record CatnipCreatedEvent(UUID id) {
}