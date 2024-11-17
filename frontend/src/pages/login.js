import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios';

function Login() {
    const [values, setValues] = useState({
        username:'',
        password:''
    })

    const [errors, setErrors] = useState({});

    const handleInput = (event) => {
        setValues(prev => ({...prev, [event.target.name]: event.target.value}))
    }

    const navigate = useNavigate();

    const handleSubmit = async (event)=> {
        event.preventDefault();
        const err = Validation(values);
        setErrors(err);
        if (Object.keys(err).length === 0) {
            const customerData = { values };
            console.log(customerData);
            try {
                
                const response = await axios.post('http://localhost:8090/api/customer/login', customerData.values);
                console.log("no errors");
                console.log(response);
                navigate('/home');
            } catch (error) {
                console.error("Error registering customer:", error);
            }
        }

    }

    const Validation = (values)=> {
        let error = {}
        if(values.username ===''){
            error.username = 'Username should not be empty'
        }

        if(values.password===''){
            error.password = 'Password should not be empty'
        }

        return error;
    }


  return (
    <div className='d-flex justify-content-center align-items-center bg-primary vh-100'>

        <div className='bg-white p-3 rounded w-2'>
            <h2>Log In</h2>
            <form action='' onSubmit={handleSubmit}>

                <div className='mb-3'>
                    <label htmlFor='username'><strong>Username</strong></label>
                    {errors.username && <span className='text-danger'> {errors.username} </span>}
                    <input type='text' placeholder='Enter Username' name='username'
                    onChange={handleInput} className='form-control rounded-0' />
                    
                </div>
                <div className='mb-3'>
                    <label htmlFor='password'><strong>Password</strong></label>
                    {errors.password && <span className='text-danger'> {errors.password} </span>}
                    <input type='password' placeholder='Enter Password' name='password'
                    onChange={handleInput} className='form-control rounded-0'/>
                    
                </div>

                <button type='submit' className='btn btn-success w-100 rounded-0'><strong>Log in</strong></button>

                <Link to='/signup' className='btn btn-default border w-100 bg-light rounded-0 text-decoration-none my-2'>Create Account</Link>

            </form>
        </div>
    </div>
  )
}

export default Login;