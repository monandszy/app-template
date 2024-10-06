package code.modules.accounts.service;

import code.modules.accounts.data.AccountDao;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class Authentication implements UserDetailsService {

  private AccountDao accountDAO;
//  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountDAO.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    List<GrantedAuthority> authorities = getAccountAuthority(account.getRoles());
    return buildAccountForAuthentication(account, authorities);
  }

  //
  private List<GrantedAuthority> getAccountAuthority(Set<Role> roles) {
    return roles.stream()
      .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
      .toList();
  }

  private UserDetails buildAccountForAuthentication(
    Account account, List<GrantedAuthority> authorities) {
    return new User(
      account.getEmail(),
      account.getPassword(),
      account.getEnabled(),
      true,
      true,
      true,
      authorities
    );
  }
}