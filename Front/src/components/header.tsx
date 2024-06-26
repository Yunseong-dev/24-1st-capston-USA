import { Link, useLocation } from 'react-router-dom';
import styles from '../css/header.module.css';
import logo from '../assets/logo.png';

const Header = () => {
   const location = useLocation();
   const col = { color: '#82cd47' };

   return (
      <header className={styles.header}>
         <div className={styles.logo}>
            <Link to="/"><img src={logo} alt="logo" /></Link>
         </div>
         <div className={styles.container}>
            
            <Link
               to="/job"
               className={styles.text}
               id={styles.job}
               style={location.pathname === '/job' ? col : {}}
            >
               구인공고
            </Link>

            <Link
               to="/equipment"
               className={styles.text}
               id={styles.equipment}
               style={location.pathname === '/equipment' ? col : {}}
            >
               장비임대
            </Link>

            <Link
               to="/chat"
               className={styles.text}
               id={styles.chat}
               style={location.pathname === '/chat' ? col : {}}
            >
               채팅
            </Link>

            <Link
               to="/me"
               id={styles.my}
               className={styles.text}
               style={
                  location.pathname === '/me' ||
                  location.pathname === '/modifyMe' ||
                  location.pathname === '/equipmentMe'
                  ? col : {}}
            >
               내정보
            </Link>

            <Link
               to="/signin"
               className={styles.text}
               id={styles.login}
               style={location.pathname === '/signin' ? col : {}}
            >
               로그인
            </Link>

            <Link
               to="/signup"
               className={styles.text}
               id={styles.signup}
               style={location.pathname === '/signup' ? col : {}}
            >
               회원가입
            </Link>

         </div>
      </header>
   );
};

export default Header;
