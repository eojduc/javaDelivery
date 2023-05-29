package quikcal;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  private static final String CONFIG_FILE = "/config.json";

  public static Configuration config() throws IOException {
    return new ObjectMapper().readValue(Application.class.getResourceAsStream(CONFIG_FILE),
        Configuration.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
