import React from 'react';
import axios from "axios";
import {useState, useEffect} from "react";
const App = () => {
  console.log('App');
  const [projects, setProjects] = useState([]);
  useEffect(() => {
    axios.get("http://localhost:8080/projects").then(response => {
      setProjects(response.data);
    });
  }, []);
  return (
    <div className="App">
      {projects.map(project => <iframe key={project.id}
          src={`https://calendar.google.com/calendar/embed?src=${project.calendarId}&ctz=America%2FLos_Angeles`} width="800" height="600"></iframe>)
      }
      <h1>Goodbye</h1>
    </div>
  );
}

export default App;
