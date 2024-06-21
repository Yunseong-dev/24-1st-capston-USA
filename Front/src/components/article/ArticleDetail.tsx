import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { customAxios, postWithToken } from "../../utils/axios";
import dayjs from "dayjs";
import useToken from "../../hooks/useToken";
import { Article } from "../../interface/article";

const ArticleDetail = () => {
   const { id } = useParams<{ id: string }>();
   const [article, setArticle] = useState<Article | null>(null);

   const navigate = useNavigate();
   const { token } = useToken();

   useEffect(() => {
      const fetchArticle = async () => {
         try {
            const response = await customAxios.get<Article>(`/articles/${id}`);
            setArticle(response.data);
         } catch (error) {
            console.error(error);
         }
      };
      fetchArticle();
   }, [id]);

   const handleChat = async () => {
      try {
         const response = await postWithToken(token, `/chat/create/article/${id}`, {});

         const roomId = response.data.roomId;
         navigate(`/chat/${roomId}`);
      } catch (error: any) {
         if (error.response && error.response.data) {
            alert(error.response.data)
         }
      }
   };

   if (!article) {
      return <div>Loading...</div>;
   }

   return (
      <div>
         <h2>{article.title}</h2>
         <p>{article.content}</p>
         <p>{dayjs(article.createdAt).format("YYYY년 MM월 DD일")}</p>
         {article.imgUrl && (
            <img
               src={article.imgUrl}
               alt="Article Image"
               style={{ maxWidth: "100px" }}
            />
         )}
         <button onClick={handleChat}>채팅하기</button>
      </div>
   );
};

export default ArticleDetail;
