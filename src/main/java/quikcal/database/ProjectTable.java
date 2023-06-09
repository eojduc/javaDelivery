package quikcal.database;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quikcal.database.repository.ProjectRepository;
import quikcal.model.Project;
@Component
public class ProjectTable {
  private final ProjectRepository projectRepository;
  private final GoogleService googleService;
  @Autowired
  public ProjectTable(ProjectRepository projectRepository, GoogleService googleService) {
    this.projectRepository = projectRepository;
    this.googleService = googleService;
  }
  public Project insert(Project project) throws IOException {
    com.google.api.services.calendar.model.Calendar googleCalendar = new com.google.api.services.calendar.model.Calendar();
    googleCalendar.setSummary(project.name());
    googleCalendar = googleService.getService().calendars()
        .insert(googleCalendar).execute();
    com.google.api.services.calendar.model.CalendarListEntry calendarListEntry = new com.google.api.services.calendar.model.CalendarListEntry();
    calendarListEntry.setId(googleCalendar.getId());
    googleService.getService().calendarList().insert(calendarListEntry).execute();
    return projectRepository.insert(new Project(project.name(), null, googleCalendar.getId()));
  }
  public Project get(String projectId) throws IOException {
    return projectRepository.findById(projectId).get();
  }

  public Project update(String projectId, Project element) throws IOException {
    return projectRepository.save(new Project(projectId, element.name(), element.calendarId()));
  }

  public void delete(String projectId) throws IOException {
    Project project = projectRepository.findById(projectId).get();
    googleService.getService().calendars().delete(project.calendarId()).execute();
    projectRepository.deleteById(projectId);
  }

  public List<Project> list() throws IOException {
    return projectRepository.findAll();
  }

}
