package code;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.modulith.Modulithic;

import java.time.Clock;
import java.time.ZoneOffset;
import java.util.TimeZone;


@Modulithic
@SpringBootApplication
public class App extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @PostConstruct
  public void setTimeZone(){
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  @Bean
  Clock customClock() {
    return Clock.system(ZoneOffset.UTC);
  }
}