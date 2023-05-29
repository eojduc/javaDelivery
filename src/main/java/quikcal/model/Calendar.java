package quikcal.model;


import jakarta.validation.constraints.NotEmpty;

public record Calendar(@NotEmpty String summary, String id) {

}
