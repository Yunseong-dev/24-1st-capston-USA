import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Signup from './pages/sign/signup';
import Signin from './pages/sign/signin';
import Main from './pages/main';
import ArticleList from './pages/article/articleList';
import CreateArticle from './pages/article/CreateArticle';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/signin" element={<Signin />} />
        <Route path="/ArticleList" element={<ArticleList />} />
        <Route path="/createArticle" element={<CreateArticle />} />
      </Routes>
    </Router>
  );
};

export default App;
