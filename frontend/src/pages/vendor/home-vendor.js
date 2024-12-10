import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import EventListVendor from '../../components/EventListVendor';
import { useUser } from '../../components/UserContext';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../HomePage.css'; 

const HomePageVendor = () => {
  const [events, setEvents] = useState([]);
  const { user, logout } = useUser();

  useEffect(() => {
    // Fetch events from backend
    fetch('http://localhost:8090/api/config/all')
      .then((res) => res.json())
      .then((data) => setEvents(data));
  }, []);

  console.log('User:', user);

  return (
    <div className="container">
      <div className="jumbotron my-4 position-relative">
        <div className="d-flex justify-content-between align-items-center">
          <div>
            <h1 className="display-4">EventTickets</h1>
            <p className="lead">Find and purchase tickets for the best events around you</p>
          </div>
          <div className="d-flex flex-column">
          {user ? (
              <>
                <span className="text-light mb-2">Welcome, {user.username}</span>
                <button onClick={logout} className="btn btn-outline-light">Logout</button>
              </>
            ) : (
              <>
                <Link to='/login' className="btn btn-outline-light mb-2">Login as Customer</Link>
                <Link to='/login-vendor' className="btn btn-outline-light">Login as Vendor</Link>
              </>
            )}
          </div>
        </div>
      </div>
      <EventListVendor events={events} />
    </div>
  );
};

export default HomePageVendor;