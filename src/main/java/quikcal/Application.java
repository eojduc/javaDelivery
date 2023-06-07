package quikcal;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import quikcal.database.Database;
import quikcal.database.repository.UserRepository;

@SpringBootApplication
@EnableMongoRepositories
public class Application {

  private static final String CONFIG_FILE = "/config.json";

  public static Config config() throws IOException {
    return new ObjectMapper().readValue(Application.class.getResourceAsStream(CONFIG_FILE),
        Config.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  public record Config(Database.Config database) {
  }

}
