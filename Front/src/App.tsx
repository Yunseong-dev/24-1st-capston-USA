import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Test from './pages/test';
import Main from './pages/main';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path="/1" element={<Test />} />
      </Routes>
    </Router>
  );
};

export default App;
