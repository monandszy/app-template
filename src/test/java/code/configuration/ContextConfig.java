package code.configuration;

import code.modules.catnip.CatnipFacade;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@ApplicationModuleTest TODO replace current context splitting, when and if they implement it.
public class ContextConfig {
  @Configuration
  @ComponentScan(basePackageClasses = CatnipFacade.class)
  public static class CatnipModuleContext {
  }
}