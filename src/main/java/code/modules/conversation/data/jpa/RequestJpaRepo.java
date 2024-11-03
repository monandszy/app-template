package code.modules.conversation.data.jpa;

import code.modules.conversation.data.RequestEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestJpaRepo extends JpaRepository<RequestEntity, UUID> {
}