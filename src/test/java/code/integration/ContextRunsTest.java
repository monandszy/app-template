package code.integration;

import code.TemplateApp;
import code.configuration.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TemplateApp.class)
@ActiveProfiles("test")
@Import(TestContainersConfig.class)
class ContextRunsTest {

  @Test
  void should_load() {
  }
}