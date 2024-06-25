import Header from '../components/header';
import styles from '../css/home.module.css';

const home = () => {
   return (
      <div>
         <Header />
         <div className={styles.main}>
            <div className={styles.option_container}>
               <a href="/job" className={styles.rectangle} id="opjob">구인공고</a>
               <a href="/equipment" className={styles.rectangle} id="opequipment">장비임대</a>
               <a href="/chat" className={styles.rectangle} id="opchat">채팅</a>
               <a href="/me" className={styles.rectangle} id="opmy">내정보</a>
            </div>
         </div>
      </div>
   );
}

export default home;
