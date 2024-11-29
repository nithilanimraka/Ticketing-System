import React from 'react';
import { Link } from 'react-router-dom';

const EventCard = ({ event }) => {
  return (
    <div className="col-md-4 mb-4">
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">{event.eventName}</h5>
          <h6 className="card-subtitle mb-2 text-muted">{event.location}</h6>
          <p className="card-text">Tickets Available: {event.no_of_tickets}</p>
          <Link to={`/event/${event.config_id}`} className="btn btn-primary">View More</Link>
        </div>
      </div>
    </div>
  );
};

export default EventCard;