import { useState, useEffect } from "react";
import { customAxios } from "../../utils/axios";
import { useNavigate } from 'react-router-dom';
import styles from '../../css/signup.module.css';
import Header from '../header';

const Signup = () => {
   const [name, setName] = useState("");
   const [phoneNumber, setPhoneNumber] = useState("");
   const [verNumber, setVerNumber] = useState("");
   const [password, setPassword] = useState("");
   const [cooldownEndTime, setCooldownEndTime] = useState<Date | null>(null);

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

      if (cooldownEndTime) {
         const currentTime = new Date();
         const remainingTime = Math.round((cooldownEndTime.getTime() - currentTime.getTime()) / 1000);

         if (remainingTime > 0) {
            return alert(`인증번호 요청이 너무 빠릅니다. ${remainingTime}초 후에 다시 시도해주세요.`);
         }
      }

      try {
         if (!phoneNumber) {
            return alert("전화번호를 작성해주세요");
         }

         if (!isValidPhoneNumber(phoneNumber)) {
            return alert("올바른 전화번호 형식이 아닙니다");
         }

         const response = await customAxios.post('/api/sms', {
            phoneNumber
         });

         alert(response.data);
         setCooldownEndTime(new Date(new Date().getTime() + 60000)); // Set cooldown for 1 minute

      } catch (error: any) {
         if (error.response && error.response.data) {
            alert(error.response.data);
         }
      }
   };

   return (
      <div>
         <Header />
         <div className={styles.main}>
            <div className={styles.box_container}>
               <h1>회원가입</h1>
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
         </div>
      </div>
   );
};

export default Signup;
