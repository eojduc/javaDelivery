package quikcal;

public record Configuration(Configuration.Database database) {
  public record Database(String type, String credentials) {
  }
}
