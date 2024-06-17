import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useToken from "../../hooks/useToken";
import { customAxios } from "../../utils/axios";

const CreateArticle = () => {
   const [title, setTitle] = useState('');
   const [content, setContent] = useState('');
   const navigate = useNavigate();
   const { token } = useToken();

   const createArticle = async (e: { preventDefault: () => void; }) => {
      e.preventDefault();

      try {
         await customAxios.post('/job/create', {
            title,
            content
         }, {
            headers: {
               'Authorization': `${token}`,
            }
         });

         navigate('/');
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
