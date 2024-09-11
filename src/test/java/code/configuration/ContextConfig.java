package code.configuration;

import code.modules.catnip.CatnipQueryFacade;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@ApplicationModuleTest TODO replace current context splitting, when and if they implement it.
public class ContextConfig {
  @Configuration
  @ComponentScan(basePackageClasses = CatnipQueryFacade.class)
  public static class CatnipModuleContext {
  }
}