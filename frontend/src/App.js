// import './App.css';
import Login from './pages/login'
import Signup from './pages/signup'
import Home from './pages/home'
import SignupVendor from './pages/signup-vendor';
import { BrowserRouter, Routes, Route} from 'react-router-dom';
import LoginVendor from './pages/login-vendor';
import EventPage from './pages/eventPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/login' element={<Login />}></Route>
        <Route path='/signup' element={<Signup />}></Route>
        <Route path='/' element={<Home />}></Route>
        <Route path='/signup-vendor' element={<SignupVendor />}></Route>
        <Route path='/login-vendor' element={<LoginVendor />}></Route>
        <Route path='/eventPage' element={<EventPage />}></Route>
      </Routes>
    </BrowserRouter>

  );
}

export default App;
