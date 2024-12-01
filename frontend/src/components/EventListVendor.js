import React from 'react';
import EventCardVendor from './EventCardVendor';

const EventListVendor = ({ events }) => {
  return (
    <div className="container">
      <div className="row">
        {events.map((event) => (
          <EventCardVendor key={event.config_id} event={event}/>
        ))}
      </div>
    </div>
  );
};

export default EventListVendor;
