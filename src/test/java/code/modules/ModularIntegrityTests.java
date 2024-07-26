package code.modules;

//import org.springframework.modulith.core.ApplicationModules;
//import org.springframework.modulith.docs.Documenter;

import code.TemplateApp;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

// https://spring.io/projects/spring-modulith
// https://docs.spring.io/spring-modulith/reference/

class ModularIntegrityTests {
//
  private final ApplicationModules modules = ApplicationModules.of(TemplateApp.class);
//
  @Test
  void test() {
    modules.forEach(System.out::println);
    modules.verify();
  }

  @Test
  void writeDocumentationSnippets() {
    new Documenter(modules, "build/reports/modulith-docs")
        .writeModulesAsPlantUml()
        .writeIndividualModulesAsPlantUml()
        .writeModuleCanvases();
  }
}