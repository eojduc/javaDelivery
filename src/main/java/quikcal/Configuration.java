package quikcal;

public record Configuration(String name, Configuration.Database database) {

  public record Database(String type, String credentials) {

  }
}
