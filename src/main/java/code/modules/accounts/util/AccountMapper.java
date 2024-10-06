package code.modules.accounts.util;

import code.configuration.SpringMapperConfig;
import code.modules.accounts.data.AccountEntity;
import code.modules.accounts.service.Account;
import code.util.Generated;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

@Mapper(config = SpringMapperConfig.class)
@AnnotateWith(Generated.class)
public interface AccountMapper {

  Account mapFromEntity(AccountEntity AccountEntity);
}