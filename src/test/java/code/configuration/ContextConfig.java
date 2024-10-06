package code.configuration;

import code.modules.accounts.AccountQueryFacade;
import code.modules.catnips.CatnipQueryFacade;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@ApplicationModuleTest TODO replace current context splitting, when and if they implement it.
public class ContextConfig {
  @Configuration
  @ComponentScan(basePackageClasses = CatnipQueryFacade.class)
  public static class CatnipModuleContext {}

  @Configuration
  @ComponentScan(basePackageClasses = AccountQueryFacade.class)
  @Import({SecurityConfig.class, DataInitializer.class})
  public static class AccountModuleContext {}
}