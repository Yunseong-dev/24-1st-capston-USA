import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useToken from "../../hooks/useToken";
import { postWithToken } from "../../utils/axios";

const CreateArticle = () => {
   const [title, setTitle] = useState('');
   const [content, setContent] = useState('');

   const { token } = useToken()
   
   const navigate = useNavigate();

   useEffect(() => {
      if (!token) {
         navigate("/")
         alert("먼저 로그인을 해주세요")
      }
   }, [token, navigate])

   const createArticle = async (e: { preventDefault: () => void; }) => {
      e.preventDefault();

      const data = { title, content };

      try {
         await postWithToken(token, '/job/create', data);

         navigate('/JobList');
         alert("게시물이 성공적으로 등록되었습니다");
      } catch (error: any) {
         alert(error.response.data);
      }
   };

   return (
      <div>
         <h1>게시물 작성</h1>
         <form onSubmit={createArticle}>
            <input
               type="text"
               value={title}
               onChange={(e) => setTitle(e.target.value)}
               placeholder="제목"
            />
            <br />
            <textarea
               value={content}
               onChange={(e) => setContent(e.target.value)}
               placeholder="내용"
               cols={30}
               rows={10}
            />
            <br />
            <button type="submit">작성</button>
         </form>
      </div>
   );
};

export default CreateArticle;
