package quikcal.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import quikcal.model.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {

}
