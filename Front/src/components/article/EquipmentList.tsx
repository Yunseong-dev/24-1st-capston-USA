import { useEffect, useState } from "react";
import { customAxios } from "../../utils/axios";
import dayjs from "dayjs";
import { Article } from "../../interface/article";
import Header from "../header";
import styles from "../../css/equipment.module.css";
import search from "../../assets/search.png";

const ArticleList = () => {
   const [articles, setArticles] = useState<Article[]>([]);
   const [searchTerm, setSearchTerm] = useState("");

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

   const filteredArticles = articles.filter((article) =>
      article.title.toLowerCase().includes(searchTerm.toLowerCase())
   );

   return (
      <div>
         <Header />
         <div className={styles.main}>
            <div className={styles.search_container}>
               <input
                  type="text"
                  className={styles.search_input}
                  placeholder="검색어를 입력해 주세요."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
               />
               <img src={search} className={styles.search_img} alt="Search" />
            </div>

            <div className={styles.write}>
               <a href="/createEquipment">
                  <button className={styles.write_button}>글 작성하기</button>
               </a>
            </div>

            <div className={styles.grid_container}>
               {filteredArticles.map((article) => (
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

export default ArticleList;
