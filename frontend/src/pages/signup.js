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
    <div className='d-flex justify-content-center align-items-center bg-primary vh-100'>
      <div className='bg-white p-3 rounded w-2'>
        <h2>Sign Up</h2>
        <form action='' onSubmit={handleSubmit}>
          <div className='mb-3'>
            <label htmlFor='name'><strong>Name</strong></label>
            <input type='text' placeholder='Enter Name' name='name'
            onChange={handleInput} className='form-control rounded-0' />
            {errors.name && <span className='text-danger'> {errors.name} </span>}
            
          </div>

          <div className='mb-3'>
            <label htmlFor='email'><strong>Email</strong></label>
            <input type='email' placeholder='Enter Email' name='email'
            onChange={handleInput} className='form-control rounded-0' />
            {errors.email && <span className='text-danger'> {errors.email} </span>}
          </div>

          <div className='mb-3'>
            <label htmlFor='username'><strong>Username</strong></label>
            <input type='text' placeholder='Enter Username' name='username'
            onChange={handleInput} className='form-control rounded-0' />
            {errors.username && <span className='text-danger'> {errors.username} </span>}
            
          </div>

          <div className='mb-3'>
            <label htmlFor='password'><strong>Password</strong></label>
            <input type='password' placeholder='Enter Password' name='password'
            onChange={handleInput} className='form-control rounded-0' />
            {errors.password && <span className='text-danger'> {errors.password} </span>}
          </div>

          <button type= 'submit' className='btn btn-success w-100 rounded-0'><strong>Create Account</strong></button>
          <p>
            Do you agree to our terms and conditions?
            <input type='checkbox'></input>
          </p>
          <Link to='/login' className='btn btn-default border w-100 bg-light rounded-0 text-decoration-none'>Login</Link>
        </form>
      </div>
    </div>
  )
}

export default Signup