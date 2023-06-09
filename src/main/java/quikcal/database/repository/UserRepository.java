package quikcal.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import quikcal.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}
