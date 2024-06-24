import { useEffect, useState } from "react";
import { customAxios } from "../../utils/axios";
import dayjs from "dayjs";
import { Article } from "../../interface/article";
import { Link } from "react-router-dom";

const ArticleList = () => {
   const [articles, setArticles] = useState<Article[]>([]);

   useEffect(() => {
      const fetchArticles = async () => {
         try {
            const response = await customAxios.get<Article[]>("/articles");
            setArticles(response.data);
         } catch (error) {
            console.error(error);
         }
      };
      fetchArticles();
   }, []);

   return (
      <div>
         <a href="/CreateArticle">게시물 생성</a>
         <ul>
            {articles.map((article) => (
               <div>
                  <Link to={`/article/${article.id}`}>
                     <h2>{article.title}</h2>
                     <p>{dayjs(article.createdAt).format("YYYY년 MM월 DD일")}</p>
                     {article.imgUrl && (
                        <img
                           src={article.imgUrl}
                           alt="Article Image"
                           style={{ maxWidth: "100px" }}
                        />
                     )}
                  </Link>
               </div>
            ))}
         </ul>
      </div>
   );
};

export default ArticleList;
