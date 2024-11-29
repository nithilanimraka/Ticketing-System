import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

const EventPage = () => {
  const { config_id } = useParams();
  const [event, setEvent] = useState(null);
  const [numTickets, setNumTickets] = useState(1);
  const [message, setMessage] = useState('');

  useEffect(() => {
    // Fetch event details from backend
    fetch(`http://localhost:8090/api/config/${config_id}`)
      .then((res) => res.json())
      .then((data) => setEvent(data));
  }, [config_id]);

  const handleBuyTickets = () => {
    // Send purchase request to backend
    fetch(`http://localhost:8090/api/customer/buy-tickets`, {
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
      })
      .catch((error) => {
        setMessage('An error occurred while processing your request.');
      });
  };

  if (!event) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container">
      <h1>{event.eventName}</h1>
      <p>Location: {event.location}</p>
      <p>Tickets Available: {event.currentTicketCount}</p>
      
      <div className="form-group">
        <label htmlFor="numTickets">Select number of tickets:</label>
        <select
          id="numTickets"
          className="form-control"
          value={numTickets}
          onChange={(e) => setNumTickets(e.target.value)}
        >
          {[...Array(event.no_of_tickets).keys()].map((num) => (
            <option key={num + 1} value={num + 1}>
              {num + 1}
            </option>
          ))}
        </select>
      </div>
      
      <button className="btn btn-primary mt-3" onClick={handleBuyTickets}> Buy Ticket</button>
      {message && <div className="alert alert-info mt-3">{message}</div>}    
    </div>
  );
};

export default EventPage;