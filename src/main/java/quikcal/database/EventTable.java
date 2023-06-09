package quikcal.database;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quikcal.database.repository.ProjectRepository;
import quikcal.model.Event;
import quikcal.model.Project;

@Component
public class EventTable {
  private final ProjectRepository projectRepository;

  private final GoogleService googleService;
  @Autowired
  public EventTable(ProjectRepository projectRepository, GoogleService googleService) {
    this.projectRepository = projectRepository;
    this.googleService = googleService;
  }
  private static Event toEvent(final com.google.api.services.calendar.model.Event googleEvent, String projectId) {
    LocalDate date;
    LocalTime start, end;
    if (googleEvent.getStart().getDateTime() != null) {
      date = Instant.ofEpochMilli(googleEvent.getStart().getDateTime().getValue())
          .atZone(ZoneId.systemDefault()).toLocalDate();
      start = Instant.ofEpochMilli(googleEvent.getStart().getDateTime().getValue())
          .atZone(ZoneId.systemDefault()).toLocalTime();
      end = Instant.ofEpochMilli(googleEvent.getEnd().getDateTime().getValue())
          .atZone(ZoneId.systemDefault()).toLocalTime();
    } else {
      date = Instant.ofEpochMilli(googleEvent.getStart().getDate().getValue())
          .atZone(ZoneId.systemDefault()).toLocalDate();
      start = LocalTime.MIN;
      end = LocalTime.MAX;
    }
    return new Event(googleEvent.getSummary(), googleEvent.getId(), date, start, end, projectId);
  }

  private static com.google.api.services.calendar.model.Event toGoogleEvent(final Event event) {
    com.google.api.services.calendar.model.Event googleEvent = new com.google.api.services.calendar.model.Event();
    googleEvent.setSummary(event.name());
    googleEvent.setStart(new EventDateTime().setDateTime(
        new DateTime(event.date().atTime(event.start()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())));
    googleEvent.setEnd(new EventDateTime().setDateTime(
        new DateTime(event.date().atTime(event.end()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())));
    return googleEvent;
  }
  public Event insert(final String projectId, final Event event) throws IOException {
    Project project = projectRepository.findById(projectId).get();
    return toEvent(
        googleService.getService().events().insert(project.calendarId(), toGoogleEvent(event)).execute(), projectId);
  }

  public Event get(final String projectId, final String eventId) throws IOException {
    Project project = projectRepository.findById(projectId).get();
    return toEvent(
        googleService.getService().events().get(project.calendarId(), eventId).execute(), projectId);
  }

  public Event update(final String projectId, final String eventId, final Event event) throws IOException {
    Project project = projectRepository.findById(projectId).get();
    return toEvent(
        googleService.getService().events().update(project.calendarId(), eventId, toGoogleEvent(event)).execute(), projectId);
  }
  public void delete(final String calendarId, final String eventId) throws IOException {
    googleService.getService().events().delete(calendarId, eventId).execute();
  }

  public List<Event> list(final String projectId) throws IOException {
    Project project = projectRepository.findById(projectId).get();
    return googleService.getService().events().list(project.calendarId()).execute().getItems().stream()
        .map(googleEvent -> toEvent(googleEvent, projectId)).toList();
  }

}
