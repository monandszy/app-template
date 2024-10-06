package code.modules.accounts.data;

import code.modules.accounts.service.Account;
import java.util.Optional;

public interface AccountDao {
  Optional<Account> findByEmail(String email);

}