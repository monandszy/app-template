package code.modules.accounts.service;

import java.util.Optional;

public interface AccountDao {
  Optional<Account> findByEmail(String email);

  Account create(Account account);

}