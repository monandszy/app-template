package code.modules.conversation.data.jpa;

import code.modules.conversation.data.QueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueryJpaRepo extends JpaRepository<QueryEntity, UUID> {
}