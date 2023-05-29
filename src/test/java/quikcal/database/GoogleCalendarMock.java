package quikcal.database;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.Calendar;

public class GoogleCalendarMock extends Calendar {

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport              HTTP transport, which should normally be:
   *                               <ul>
   *                               <li>Google App Engine:
   *                               {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *                               <li>Android: {@code newCompatibleTransport} from
   *                               {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *                               <li>Java: {@link GoogleNetHttpTransport#newTrustedTransport()}
   *                               </li>
   *                               </ul>
   * @param jsonFactory            JSON factory, which may be:
   *                               <ul>
   *                               <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *                               <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *                               <li>Android Honeycomb or higher:
   *                               {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *                               </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public GoogleCalendarMock(HttpTransport transport,
      JsonFactory jsonFactory,
      HttpRequestInitializer httpRequestInitializer) {
    super(transport, jsonFactory, httpRequestInitializer);
  }
}
