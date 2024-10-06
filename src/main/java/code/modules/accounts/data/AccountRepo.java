package code.modules.accounts.data;

import code.modules.accounts.service.Account;
import code.modules.accounts.service.AccountDao;
import code.modules.accounts.util.AccountMapper;
import code.util.RepositoryAdapter;
import java.util.Optional;
import lombok.AllArgsConstructor;

@RepositoryAdapter
@AllArgsConstructor
public class AccountRepo implements AccountDao {

  private AccountMapper accountMapper;
  private AccountJpaRepo accountJpaRepo;

  @Override
  public Optional<Account> findByEmail(String email) {
    Optional<AccountEntity> byUserName = accountJpaRepo.findByEmail(email);
    return byUserName.map(e -> accountMapper.entityToDomain(e));
  }

  @Override
  public Account create(Account account) {
    return accountMapper.entityToDomain(accountJpaRepo
      .save(accountMapper.domainToEntity(account)));
  }

}