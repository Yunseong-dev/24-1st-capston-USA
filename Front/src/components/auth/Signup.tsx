import { useState } from "react";
import { customAxios } from "../../utils/axios";
import { useNavigate } from 'react-router-dom';
import styles from '../../css/signup.module.css';
import Header from '../header';

const Signup = () => {
   const [name, setName] = useState("");
   const [phoneNumber, setPhoneNumber] = useState("");
   const [verNumber, setVerNumber] = useState("");
   const [password, setPassword] = useState("");

   const navigate = useNavigate();

   const isValidPhoneNumber = (phoneNumber: string) => {
      const regex = /^\d{11}$/;
      return regex.test(phoneNumber);
   };

   const isValidPassword = (password: string) => {
      return password.length >= 4;
   };

   const signup = async (e: any) => {
      e.preventDefault();

      try {
         if (!name || !phoneNumber || !verNumber || !password) {
            return alert('모든 입력란을 작성해주세요');
         }

         if (!isValidPhoneNumber(phoneNumber)) {
            return alert("올바른 전화번호 형식이 아닙니다");
         }

         if (!isValidPassword(password)) {
            return alert("비밀번호는 4자리 이상이어야 합니다");
         }

         await customAxios.post('/api/user/signup', {
            name,
            phoneNumber,
            verNumber,
            password
         });

         navigate('/');
         alert("회원가입에 성공하였습니다");

      } catch (error: any) {
         if (error.response && error.response.data) {
            alert(error.response.data);
         }
      }
   };

   const verificationCode = async (e: any) => {
      e.preventDefault();

      try {
         if (!phoneNumber) {
            return alert("전화번호를 작성해주세요");
         }

         if (!isValidPhoneNumber(phoneNumber)) {
            return alert("올바른 전화번호 형식이 아닙니다");
         }

         await customAxios.post('/api/sms', {
            phoneNumber
         });

         alert("인증번호가 전송되었습니다");

      } catch (error: any) {
         if (error.response && error.response.data) {
            alert(error.response.data);
         }
      }
   };

   return (
      <div>
         <Header />
         <main className={styles.main}>
            <div className={styles.box_container}>
               <div className={styles.main_text}><p>회원가입</p></div>
               <div className={styles.box}>
                  <form onSubmit={signup}>
                     <div className={styles.name}>
                        <input
                           type="text"
                           value={name}
                           onChange={(e) => setName(e.target.value)}
                           placeholder="이름"
                        />
                     </div>
                     <div className={styles.number}>
                        <input
                           type="text"
                           value={phoneNumber}
                           onChange={(e) => setPhoneNumber(e.target.value)}
                           placeholder="전화번호"
                        />
                     </div>
                     <div className={styles.password}>
                        <input
                           type="password"
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}
                           placeholder="비밀번호"
                        />
                     </div>
                     <div className={styles.button_container}>
                        <div className={styles.certified}>
                           <input
                              type="text"
                              value={verNumber}
                              onChange={(e) => setVerNumber(e.target.value)}
                              placeholder="인증번호"
                           />
                        </div>
                        <div className={styles.cerbutton}>
                           <button onClick={verificationCode} type="button">인증번호 전송</button>
                        </div>
                     </div>
                     <div className={styles.subbutton}>
                        <button type="submit">가입하기</button>
                     </div>
                  </form>
               </div>
            </div>
         </main>
      </div>
   );
};

export default Signup;
