package code.modules.accounts.data;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AccountJpaRepo extends JpaRepository<AccountEntity, Integer> {
  @EntityGraph(
    attributePaths = {
      "authorities"
    }
  )
  Optional<AccountEntity> findByEmail(String email);
}