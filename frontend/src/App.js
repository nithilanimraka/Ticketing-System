// import './App.css';
import { BrowserRouter, Routes, Route} from 'react-router-dom';
import Login from './pages/customer/login'
import Signup from './pages/customer/signup'
import Home from './pages/customer/home'
import SignupVendor from './pages/vendor/signup-vendor';
import LoginVendor from './pages/vendor/login-vendor';
import EventPage from './pages/customer/eventPage';
import EventPageVendor from './pages/vendor/eventPage-vendor';
import HomePageVendor from './pages/vendor/home-vendor';

import { UserProvider } from './components/UserContext';

function App() {
  return (
    <UserProvider>
      <BrowserRouter>
        <Routes>
          <Route path='/login' element={<Login />}></Route>
          <Route path='/signup' element={<Signup />}></Route>
          <Route path='/' element={<Home />}></Route>
          <Route path='/signup-vendor' element={<SignupVendor />}></Route>
          <Route path='/login-vendor' element={<LoginVendor />}></Route>
          <Route path='/event/:config_id' element={<EventPage />}></Route>
          <Route path='/event-vendor/:config_id' element={<EventPageVendor />}></Route>
          <Route path='/home-vendor' element={<HomePageVendor />}></Route>
        </Routes>
      </BrowserRouter>
    </UserProvider>

  );
}

export default App;
