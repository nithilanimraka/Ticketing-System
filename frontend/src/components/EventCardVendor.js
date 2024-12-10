import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useUser } from '../components/UserContext';

const EventCard = ({ event }) => {
  const { user } = useUser();
  const navigate = useNavigate();

  const handleViewMore = () => {
    console.log('User:', user);
    if (user) {
      navigate(`/event-vendor/${event.config_id}`);
    } else {
      navigate('/login-vendor');
    }
  };

  return (
    <div className="col-md-4 mb-4">
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">{event.eventName}</h5>
          <h6 className="card-subtitle mb-2 text-muted">{event.location}</h6>
          <p className="card-text">Tickets Available: {event.currentTicketCount}</p>
          <button onClick={handleViewMore} className="btn btn-primary">View More</button>
        </div>
      </div>
    </div>
  );
};

export default EventCard;