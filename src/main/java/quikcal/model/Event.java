package quikcal.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public record Event(@NotEmpty String name, String id, @NotNull LocalDate date, @NotNull LocalTime start,
                    @NotNull LocalTime end, String projectId) {
}
