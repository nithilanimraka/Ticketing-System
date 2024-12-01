import { useState } from 'react';
import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios';

function Signup() {

  const [values, setValues] = useState({
    name:'',
    email:'',
    username:'',
    password:''
  })

  const [errors, setErrors] = useState({});

  const navigate = useNavigate();

  const handleInput = (event) => {
    setValues(prev => ({...prev, [event.target.name]: event.target.value}))
  }

  const handleSubmit = async (event)=> {

    event.preventDefault();
    const err = Validation(values);
    setErrors(err);
    if (Object.keys(err).length === 0) {
      const customerData = { values };
      console.log(customerData);
      try {
        console.log("no errors");
        const response = await axios.post('http://localhost:8090/api/customer/register', customerData.values);
        console.log(response);
        navigate('/login');

      } catch (error) {
        console.error("Error registering customer:", error);
      }
    }
  }

  const Validation = (values) => {
    let error = {};

    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;


    if (values.name === '') {
      error.name = 'Name should not be empty'
    }

    if (values.email === '') {
      error.email = 'Email should not be empty'
    }

    else if (!emailPattern.test(values.email)) {
      error.email = 'Email is not valid'
    }

    if (values.username === '') {
      error.username = 'Username should not be empty'
    }

    if (values.password === '') {
      error.password = 'Password should not be empty'
    }

    return error;
  }

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow-lg p-4" style={{ width: '400px' }}>
        <h3 className="text-center text-primary mb-4">Customer Sign Up</h3>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="name" className="form-label">
              <strong>Name</strong>
            </label>
            {errors.name && <span className="text-danger"> {errors.name} </span>}
            <input
              type="text"
              placeholder="Enter Name"
              name="name"
              onChange={handleInput}
              className="form-control"
            />
          </div>

          <div className="mb-3">
            <label htmlFor="email" className="form-label">
              <strong>Email</strong>
            </label>
            {errors.email && <span className="text-danger"> {errors.email} </span>}
            <input
              type="email"
              placeholder="Enter Email"
              name="email"
              onChange={handleInput}
              className="form-control"
            />
          </div>

          <div className="mb-3">
            <label htmlFor="username" className="form-label">
              <strong>Username</strong>
            </label>
            {errors.username && <span className="text-danger"> {errors.username} </span>}
            <input
              type="text"
              placeholder="Enter Username"
              name="username"
              onChange={handleInput}
              className="form-control"
            />
          </div>

          <div className="mb-3">
            <label htmlFor="password" className="form-label">
              <strong>Password</strong>
            </label>
            {errors.password && <span className="text-danger"> {errors.password} </span>}
            <input
              type="password"
              placeholder="Enter Password"
              name="password"
              onChange={handleInput}
              className="form-control"
            />
          </div>

          <div className="form-check mb-3">
            <input
              type="checkbox"
              className="form-check-input"
              id="termsCheckbox"
            />
            <label className="form-check-label" htmlFor="termsCheckbox">
              I agree to the terms and conditions
            </label>
          </div>

          <button type="submit" className="btn btn-primary w-100 mb-3">
            Create Account
          </button>
          <div className="d-flex justify-content-between align-items-center">
            <span>Already have an account?</span>
            <Link
              to="/login"
              className="btn btn-outline-secondary text-decoration-none ms-1"
            >
              Log In
            </Link>
          </div>
        </form>
      </div>
    </div>
  )
}

export default Signup;