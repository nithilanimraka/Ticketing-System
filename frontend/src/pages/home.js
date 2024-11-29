import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import EventList from '../components/EventList';
import 'bootstrap/dist/css/bootstrap.min.css';
import './HomePage.css'; 

const HomePage = () => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Fetch events from backend
    fetch('http://localhost:8090/api/config/all')
      .then((res) => res.json())
      .then((data) => setEvents(data));
  }, []);

  return (
    <div className="container">
      <div className="jumbotron my-4 position-relative">
        <div className="d-flex justify-content-between align-items-center">
          <div>
            <h1 className="display-4">EventTickets</h1>
            <p className="lead">Find and purchase tickets for the best events around you</p>
          </div>
          <div className="d-flex flex-column">
            <Link to='/login' className="btn btn-outline-light mb-2">Login as Customer</Link>
            <Link to='/login-vendor' className="btn btn-outline-light">Login as Vendor</Link>
          </div>
        </div>
      </div>
      <EventList events={events} />
    </div>
  );
};

export default HomePage;