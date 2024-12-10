import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useUser } from '../../components/UserContext';
import axios from 'axios';

function LoginVendor() {
    const [values, setValues] = useState({
        username:'',
        password:''
    })

    const { login } = useUser();

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
            const vendorData = { values };
            console.log(vendorData);
            try {
                
                const response = await axios.post('http://localhost:8090/api/vendor/login', vendorData.values, {
                    username: values.username,
                });
                if(!response.data.status){
                    alert("Invalid Username or Password");
                    return;
                }
                console.log("no errors");
                console.log(response);
                const userData = response.data; 
                login(userData);
                navigate('/home-vendor');
            } catch (error) {
                console.error("Error registering vendor:", error);
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
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow-lg p-4" style={{ width: '400px' }}>
        <h3 className="text-center text-primary mb-4">Vendor Log In</h3>
        <form onSubmit={handleSubmit}>
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
          <button type="submit" className="btn btn-primary w-100 mb-3">
            Log In
          </button>
          <div className="d-flex justify-content-between align-items-center">
            <span>Don't have an account?</span>
            <Link
              to="/signup-vendor"
              className="btn btn-outline-secondary text-decoration-none ms-1"
            >
              Create Account
            </Link>
          </div>
        </form>
      </div>
    </div>
  )
}

export default LoginVendor;