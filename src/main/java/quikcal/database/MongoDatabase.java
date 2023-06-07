package quikcal.database;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quikcal.database.Database.UserTable;
import quikcal.database.repository.UserRepository;
import quikcal.model.User;

@Component
public class MongoDatabase {
  @Autowired
  private UserRepository userRepository;
  public UserTable users() {
    return new UserTable() {
      @Override
      public User insert(User user) {
        return userRepository.save(user);
      }

      @Override
      public User get(String userId) {
        return userRepository.findById(userId).orElse(null);
      }

      @Override
      public User update(String userId, User element) {
        return userRepository.save(element);
      }

      @Override
      public void delete(String userId) {
        userRepository.deleteById(userId);
      }

      @Override
      public List<User> list() {
        return userRepository.findAll();
      }
    };
  }



}
