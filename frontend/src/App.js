import React from 'react';
import axios from "axios";
import {useState, useEffect} from "react";
const App = () => {
  console.log('App');
  const [calendars, setCalendars] = useState([]);
  useEffect(() => {
    axios.get("/calendars").then(response => {
      console.log(response.data);
      setCalendars(response.data);
    });
  }, []);
  return (
    <div className="App">
      {calendars.map(calendar => <iframe key={calendar.id}
          src={`https://calendar.google.com/calendar/embed?src=${calendar.id}&ctz=America%2FLos_Angeles`} width="800" height="600"></iframe>)
      }
      <h1>Hello</h1>
    </div>
  );
}

export default App;
