import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Test from './pages/test';
import Test2 from './pages/test2';
import Main from './pages/main';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path="/1" element={<Test />} />
        <Route path="/2" element={<Test2 />} />
      </Routes>
    </Router>
  );
};

export default App;
