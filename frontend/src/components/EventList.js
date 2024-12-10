import React from 'react';
import EventCard from './EventCard';

const EventList = ({ events }) => {
  return (
    <div className="container">
      <div className="row">
        {events.map((event) => (
          <EventCard key={event.config_id} event={event}/>
        ))}
      </div>
    </div>
  );
};

export default EventList;
