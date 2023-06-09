package quikcal.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quikcal.database.UserTable;
import quikcal.model.User;

@RestController
@RequestMapping("/users")
public class UsersController implements Controller {
  private final UserTable users;
  @Autowired
  public UsersController(UserTable users) {
    this.users = users;
  }

  @GetMapping("")
  List<User> list() throws Exception {
    return users.list();
  }

  @GetMapping("{userId}")
  User get(@PathVariable String userId) throws Exception {
    return users.get(userId);
  }
  @PatchMapping("{userId}")
  User patch(@PathVariable String userId, @RequestBody User user) throws Exception {
    return users.update(userId, user);
  }
  @PostMapping("")
  User post(@RequestBody User user) throws Exception {
    return users.insert(user);
  }
  @DeleteMapping("{userId}")
  String delete(@PathVariable String userId) throws Exception {
    users.delete(userId);
    return "User deleted";
  }
}
