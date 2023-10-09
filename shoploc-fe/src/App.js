import './App.css';

import React, { useState, useEffect } from 'react';

function App() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Use the fetch API to get data from an endpoint
    fetch('http://localhost:8080/users')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then((responseData) => {
        setData(responseData); // Update the state with the fetched data
        setLoading(false); // Set loading to false
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
        setLoading(false);
      });
  }, []); // The empty dependency array ensures this effect runs once, similar to componentDidMount

  return (
    <div>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <ul>
          {data.map((item) => (
            <li key={item.firstname}>{item.lastname}</li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default App;
