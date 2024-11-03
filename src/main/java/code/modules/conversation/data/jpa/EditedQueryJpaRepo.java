package code.modules.conversation.data.jpa;

import code.modules.conversation.data.EditedQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EditedQueryJpaRepo extends JpaRepository<EditedQueryEntity, UUID> {
}