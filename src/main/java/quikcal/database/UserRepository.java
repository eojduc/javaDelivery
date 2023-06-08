package quikcal.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import quikcal.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}
