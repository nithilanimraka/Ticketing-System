import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import '../EventPage.css'; 

const EventPageVendor = () => {
  const { config_id } = useParams();
  const [event, setEvent] = useState(null);
  const [numTickets, setNumTickets] = useState(1);
  const [message, setMessage] = useState('');

  const fetchEventDetails = () => {
    // Fetch event details from backend
    fetch(`http://localhost:8090/api/config/${config_id}`)
      .then((res) => res.json())
      .then((data) => setEvent(data));
  };

  useEffect(() => {
    fetchEventDetails();
  }, [config_id]);

  const handleAddTickets = () => {
    // Send purchase request to backend
    fetch(`http://localhost:8090/api/vendor/add-tickets`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        configId: config_id,
        count: numTickets,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        setMessage(data.message);
        fetchEventDetails();
      })
      .catch((error) => {
        setMessage('An error occurred while processing your request.');
      });
  };

  if (!event) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mt-5">
      <div className="card">
        <div className="card-header bg-primary text-white">
          <h1>{event.eventName}</h1>
        </div>
        <div className="card-body">
          <p className="card-text"><strong>Location:</strong> {event.location}</p>
          <p className="card-text"><strong>Tickets Available:</strong> {event.currentTicketCount}</p>
          <div className="form-group">
            <label htmlFor="numTickets">Select number of tickets:</label>
            <input
              type="number"
              id="numTickets"
              className="form-control"
              value={numTickets}
              onChange={(e) => setNumTickets(e.target.value)}
              min="1"
            />
          </div>
          <button className="btn btn-primary mt-3" onClick={handleAddTickets}>Add Ticket</button>
          {message && <div className="alert alert-info mt-3">{message}</div>}
        </div>
      </div>
    </div>
  );
};

export default EventPageVendor;