import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NotFound from './pages/404';
import Signup from './components/auth/Signup';
import Signin from './components/auth/Signin';
import Home from './pages/Home';
import EquipmentList from './components/article/EquipmentList';
import CreateEquipment from './components/article/CreateEquipment';
import EquipmentDetail from './components/article/EquipmentDetail';
import JobList from './components/job/JobList';
import CreateJob from './components/job/CreateJob';
import ChatRoom from './components/chat/ChatRoom';
import JobDetail from './components/job/JobDetail';
import ChatList from './components/chat/ChatList';
const App = () => {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Home />} />

        <Route path="/Signup" element={<Signup />} />
        <Route path="/Signin" element={<Signin />} />

        <Route path="/Equipment" element={<EquipmentList />} />
        <Route path="/CreateEquipment" element={<CreateEquipment />} />
        <Route path="/Equipment/:id" element={<EquipmentDetail />} />

        <Route path="/Job" element={<JobList />} />
        <Route path="/CreateJob" element={<CreateJob />} />
        <Route path="/Job/:id" element={<JobDetail />} />

        <Route path="/chat" element={<ChatList />} />
        <Route path="/Chat/:chatRoomId" element={<ChatRoom />} />
        
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
};

export default App;
