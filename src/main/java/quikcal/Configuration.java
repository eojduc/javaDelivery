package quikcal;

public record Configuration(String name, Database database) {

  public record Database(String type, String credentials) {

  }
}
