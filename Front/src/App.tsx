import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NotFound from './pages/404';
import Signup from './components/auth/Signup';
import Signin from './components/auth/Signin';
import Home from './pages/Home';
import ArticleList from './components/article/ArticleList';
import CreateArticle from './components/article/CreateArticle';
import ArticleDetail from './components/article/ArticleDetail';
import JobPosts from './components/job/JobPost';
import CreateJobPost from './components/job/CreateJobPost';
import ChatRoom from './components/chat/ChatRoom';

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
        <Route path="/Jobposts" element={<JobPosts />} />
        <Route path="/CreateJobpost" element={<CreateJobPost />} />
        <Route path="/Chat/:chatRoomId" element={<ChatRoom />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
};

export default App;
