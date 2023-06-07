package quikcal.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public record User(@NotEmpty String name, @NotEmpty String number, String chatHistory, @Id String id) {

}
