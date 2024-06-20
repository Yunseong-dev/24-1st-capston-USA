import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NotFound from './pages/404';
import Signup from './components/auth/Signup';
import Signin from './components/auth/Signin';
import Home from './pages/Home';
import ArticleList from './components/article/ArticleList';
import CreateArticle from './components/article/CreateArticle';
import ArticleDetail from './components/article/ArticleDetail';
import JobList from './components/job/JobList';
import CreateJob from './components/job/CreateJob';
import ChatRoom from './components/chat/ChatRoom';
import ChatList from './components/chat/ChatList';
const App = () => {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path="/Signup" element={<Signup />} />
        <Route path="/Signin" element={<Signin />} />
        <Route path="/ArticleList" element={<ArticleList />} />
        <Route path="/CreateArticle" element={<CreateArticle />} />
        <Route path="/Article/:id" element={<ArticleDetail />} />
        <Route path="/JobList" element={<JobList />} />
        <Route path="/CreateJob" element={<CreateJob />} />
        <Route path="/Chat/:chatRoomId" element={<ChatRoom />} />
        <Route path="/chatList" element={<ChatList />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
};

export default App;
