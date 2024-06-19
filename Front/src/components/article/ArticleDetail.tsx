import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { customAxios } from "../../utils/axios";
import dayjs from "dayjs";
import { Article } from "../../interface/article";

const ArticleDetail = () => {
   const { id } = useParams<{ id: string }>();
   const [article, setArticle] = useState<Article | null>(null);

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

   if (!article) {
      return <div>Loading...</div>;
   }

   return (
      <div>
         <h2>{article.title}</h2>
         <p>{article.content}</p>
         <p>{dayjs(article.createdAt).format("YYYY년 MM월 DD일")}</p>
         {article.filename && (
            <img
               src={`http://localhost:8080/imgFile/${article.filename}`}
               alt="Article Image"
               style={{ maxWidth: "100px" }}
            />
         )}
      </div>
   );
};

export default ArticleDetail;
