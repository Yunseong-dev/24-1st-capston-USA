import { Link } from 'react-router-dom';
import styles from '../css/header.module.css';
import logo from '../assets/logo.png';

const Header = () => {
   return (
      <header className={styles.header}>
         <div className={styles.logo}>
            <Link to="/"><img src={logo} alt="logo" /></Link>
         </div>
         <div className={styles.container}>
            <Link to="/job" className={styles.text} id={styles.job}>구인공고</Link>
            <Link to="/equipment" className={styles.text} id={styles.equipment}>장비임대</Link>
            <Link to="/chat" className={styles.text} id={styles.chat}>채팅</Link>
            <Link to="/my" id={styles.my} className={styles.text}>내정보</Link>
            <Link to="/signin" className={styles.text} id={styles.login}>로그인</Link>
            <Link to="/signup" className={styles.text} id={styles.signup}>회원가입</Link>
         </div>
      </header>
   );
};

export default Header;
