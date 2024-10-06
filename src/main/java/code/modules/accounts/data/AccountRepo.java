package code.modules.accounts.data;

import code.modules.accounts.service.Account;
import code.modules.accounts.util.AccountMapper;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccountRepo implements AccountDao {

  private AccountMapper entityMapper;
  private AccountJpaRepo accountJpaRepo;

  @Override
  public Optional<Account> findByEmail(String email) {
    Optional<AccountEntity> byUserName = accountJpaRepo.findByEmail(email);
    return byUserName.map(e -> entityMapper.mapFromEntity(e));
  }

}