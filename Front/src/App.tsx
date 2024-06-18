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
import ChatRoom from './pages/job/Chat';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/signin" element={<Signin />} />
        <Route path="/ArticleList" element={<ArticleList />} />
        <Route path="/createArticle" element={<CreateArticle />} />
        <Route path="/article/:id" element={<ArticleDetail />} />
        <Route path="/jobposts" element={<JobPosts />} />
        <Route path="/create-jobpost" element={<CreateJobPost />} />
        <Route path="/chat/:chatRoomId" element={<ChatRoom />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
};

export default App;
