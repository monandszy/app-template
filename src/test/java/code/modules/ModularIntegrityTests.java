package code.modules;

import code.TemplateApp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

// https://spring.io/projects/spring-modulith
// https://docs.spring.io/spring-modulith/reference/
@Slf4j
@SpringBootTest(classes = TemplateApp.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ModularIntegrityTests {
  private final ApplicationModules modules = ApplicationModules.of(TemplateApp.class);
  private ApplicationContext applicationContext;

  @Test
  @Disabled
  void print_modules() {
    modules.forEach(System.out::println);
    modules.verify();
  }

  @Test
  void should_write_documentation() {
    new Documenter(modules, "build/reports/modulith-docs")
      .writeModulesAsPlantUml()
      .writeIndividualModulesAsPlantUml()
      .writeModuleCanvases();
  }

  @Test
  void should_run() {
  }

  @Test
  @Disabled
  void print_initialized_beans() {
    String[] beanNames = applicationContext.getBeanDefinitionNames();
    log.info("Beans initialized in the context:");
    for (String beanName : beanNames) {
      log.info(beanName);
    }
  }
}