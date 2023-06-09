package quikcal.database;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quikcal.database.repository.UserRepository;
import quikcal.model.User;
@Component
public class UserTable {
  private final UserRepository userRepository;
  @Autowired
  public UserTable(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  public User insert(User user) {
    return userRepository.save(user);
  }

  public User get(String userId) {
    return userRepository.findById(userId).orElse(null);
  }

  public User update(String userId, User element) {
    return userRepository.save(new User(userId, element.name(), element.chatHistory(), element.id()));
  }

  public void delete(String userId) {
    userRepository.deleteById(userId);
  }

  public List<User> list() {
    return userRepository.findAll();
  }

}
