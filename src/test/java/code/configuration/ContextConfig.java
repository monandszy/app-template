package code.configuration;

import code.modules.accounts.AccountQueryFacade;
import code.modules.catnips.CatnipQueryFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

public class ContextConfig {
  @Configuration
  @ComponentScan(basePackageClasses = CatnipQueryFacade.class)
  public static class CatnipModuleContext {}

  @Configuration
  @ComponentScan(basePackageClasses = AccountQueryFacade.class)
  public static class AccountModuleContext {
    @Bean // Enables method validation
    public MethodValidationPostProcessor methodValidationPostProcessor() {
      return new MethodValidationPostProcessor();
    }
  }
}