// import './App.css';
import Login from './pages/login'
import Signup from './pages/signup'
import Home from './pages/home'
import { BrowserRouter, Routes, Route} from 'react-router-dom';
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/login' element={<Login />}></Route>
        <Route path='/signup' element={<Signup />}></Route>
        <Route path='/' element={<Home />}></Route>
      </Routes>
    </BrowserRouter>

  );
}

export default App;
