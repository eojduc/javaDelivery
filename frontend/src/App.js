import axios from "axios";
import {React, useState, useEffect} from "react";
const App = () => {
  const [projects, setProjects] = useState([]);
  const [selectedProject, setSelectedProject] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:8080/projects").then(response => {
      setProjects(response.data);
    });
  }, []);
  const projectSelectHandler = (event) => {
    projects.filter(project => project.id === event.target.value)
      .forEach(project =>
          setSelectedProject(project))
  }
  return (
    <div className="App" >
      <select onChange={projectSelectHandler}>
        <option value={null}>Select a project</option>
        {projects.map(project => <option key={project.id} value={project.id}>{project.name}</option>)}
      </select>
      {selectedProject && <iframe title={selectedProject.name} key={selectedProject.id}
                                  src={`https://calendar.google.com/calendar/embed?src=${selectedProject.calendarId}`} width="800" height="600"></iframe>}
    </div>
  );
}

export default App;
