package quikcal.controller;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quikcal.database.ProjectTable;
import quikcal.model.Project;

@RestController
@RequestMapping("/projects")
public class ProjectsController implements Controller {
  private final ProjectTable projects;

  @Autowired
  public ProjectsController(@NotNull ProjectTable projects) {
    this.projects = projects;
  }

  @GetMapping("")
  List<Project> list() throws Exception {
    return projects.list();
  }

  @GetMapping("{projectId}")
  Project get(@PathVariable String projectId) throws Exception {
    return projects.get(projectId);
  }
  @PatchMapping("{projectId}")
  Project patch(@PathVariable String projectId, @RequestBody Project project) throws Exception {
    return projects.update(projectId, project);
  }
  @PostMapping("")
  Project post(@RequestBody Project project) throws Exception {
    return projects.insert(project);
  }
  @DeleteMapping("{projectId}")
  String delete(@PathVariable String projectId) throws Exception {
    projects.delete(projectId);
    return "Project deleted";
  }

}
