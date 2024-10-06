package code.modules.accounts.data;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepo extends JpaRepository<AccountEntity, Integer> {
  @EntityGraph(
    attributePaths = {
      "roles"
    }
  )
  Optional<AccountEntity> findByEmail(String email);
}