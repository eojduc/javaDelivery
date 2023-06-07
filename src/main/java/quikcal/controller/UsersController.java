package quikcal.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quikcal.database.Database;
import quikcal.model.User;

@RestController
@RequestMapping("/users")
public class UsersController implements Controller {
  @Autowired
  private Database database;

  @GetMapping("")
  List<User> list() throws Exception {
    return this.database.users().list();
  }

  @GetMapping("{userId}")
  User get(String userId) throws Exception {
    return this.database.users().get(userId);
  }
  @PatchMapping("{userId}")
  User patch(String userId, @RequestBody User user) throws Exception {
    return this.database.users().update(userId, user);
  }
  @PostMapping("")
  User post(@RequestBody User user) throws Exception {
    return this.database.users().insert(user);
  }
}
