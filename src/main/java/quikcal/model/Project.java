package quikcal.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("projects")
public record Project(@NotEmpty String name, String id, String calendarId) {
}
