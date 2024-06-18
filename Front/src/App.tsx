import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NotFound from './pages/404';
import Signup from './pages/sign/signup';
import Signin from './pages/sign/signin';
import Main from './pages/main';
import ArticleList from './pages/article/articleList';
import CreateArticle from './pages/article/createArticle';
import ArticleDetail from './pages/article/articleDetail';
import JobPosts from './pages/job/JobPost';
import CreateJobPost from './pages/job/CreateJobPost';
import ChatRoom from './pages/job/ChatRoom';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main />} />
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
