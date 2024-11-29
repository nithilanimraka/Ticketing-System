import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

const EventPage = () => {
  const { config_id } = useParams();
  const [event, setEvent] = useState(null);

  useEffect(() => {
    // Fetch event details from backend
    fetch(`http://localhost:8090/api/config/${config_id}`)
      .then((res) => res.json())
      .then((data) => setEvent(data));
  }, [config_id]);

  if (!event) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container">
      <h1>{event.eventName}</h1>
      <p>Details for event ID: {config_id}</p>
      {/* Add more event details here */}
    </div>
  );
};

export default EventPage;