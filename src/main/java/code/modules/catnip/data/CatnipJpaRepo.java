package code.modules.catnip.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CatnipJpaRepo extends JpaRepository<CatnipEntity, UUID> {
}