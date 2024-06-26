import { useEffect, useState } from "react";
import { fetcherWithToken } from "../../utils/axios";
import dayjs from "dayjs";
import useToken from "../../hooks/useToken";
import { Article } from "../../interface/article";
import styles from "../../css/equipment.module.css";
import Header from "../header";

const EquipmentMe = () => {
   const [articles, setArticles] = useState<Article[]>([]);

   const { token } = useToken();

   useEffect(() => {
      const fetchArticles = async () => {
         try {
            const response = await fetcherWithToken(token, `/articles`);
            setArticles(response.data);
         } catch (error) {
            console.error(error);
         }
      };
      fetchArticles();
   }, [token]);

   if (articles.length === 0) {
      return (
         <div>
            <Header />
            <h3>올라온 게시물이 없습니다</h3>
         </div>
      );
   }

   return (
      <div>
         <Header />
         <div className={styles.main}>
            <div className={`${styles.grid_container} ${styles.center}`}>
               {articles.map((article) => (
                  <a href={`/equipment/${article.id}`} key={article.id}>
                     <div className={styles.grid_item}>
                        {article.imgUrl && (
                           <img src={article.imgUrl} alt="Article Image" className={styles.image} />
                        )}
                        <p>모델명: {article.title}</p>
                        <p>등록일: {dayjs(article.createdAt).format("YYYY년 MM월 DD일")}</p>
                     </div>
                  </a>
               ))}
            </div>
         </div>
      </div>
   );
};

export default EquipmentMe;
