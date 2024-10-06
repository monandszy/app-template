package code.modules.catnips.data;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatnipJpaRepo extends JpaRepository<CatnipEntity, UUID> {
}