package quikcal.database;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * creates a google service object.
 */

@Component
public class GoogleService {
  /**
   * Directory to store authorization tokens for this application.
   */
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  private final com.google.api.services.calendar.Calendar service;
  public GoogleService() throws Exception {
    final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    Credential credential = getCredentials(httpTransport, CREDENTIALS_FILE_PATH, List.of(
        CalendarScopes.CALENDAR), jsonFactory);
    this.service = new com.google.api.services.calendar.Calendar.Builder(httpTransport,
        jsonFactory,
        credential).build();
  }

  /**
   * Creates an authorized Credential object.
   */
  private static Credential getCredentials(final NetHttpTransport httpTransport,
      final String credentialsFilePath, final List<String> scopes, final JsonFactory jsonFactory) throws IOException {
    // Load client secrets.
    InputStream in = GoogleService.class.getResourceAsStream(credentialsFilePath);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
    }
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
        new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport,
        jsonFactory, clientSecrets, scopes).setDataStoreFactory(
            new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH))).setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    //returns an authorized Credential object.
    return credential;
  }
  public com.google.api.services.calendar.Calendar getService() {
    return service;
  }

}
