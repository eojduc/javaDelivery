package quikcal.model;

import java.time.Instant;

public record Event(String name, String id, Instant start, Instant end) {

}
