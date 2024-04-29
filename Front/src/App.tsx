import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Signup from './pages/sign/signup';
import Signin from './pages/sign/signin';
import Main from './pages/main';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/signin" element={<Signin />} />
      </Routes>
    </Router>
  );
};

export default App;
