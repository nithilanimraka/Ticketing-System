import React from 'react';
import EventCard from './EventCard';

const EventList = ({ events }) => {
  return (
    <div className="container">
      <div className="row">
        {events.map((event) => (
          <EventCard key={event.eventName} event={event}/>
        ))}
      </div>
    </div>
  );
};

export default EventList;
