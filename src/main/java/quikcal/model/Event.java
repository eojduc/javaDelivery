package quikcal.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record Event(@NotEmpty String name, String id, @NotNull Instant start,
                    @NotNull Instant end) {
}
