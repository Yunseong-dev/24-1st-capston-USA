import { useState } from "react";
import { putWithToken } from "../../utils/axios";
import { useNavigate } from 'react-router-dom';
import useToken from "../../hooks/useToken";
import Header from "../header";
import Styles from "../../css/signup.module.css";

const ModifyMe = () => {
   const [name, setName] = useState("");
   const [password, setPassword] = useState("");
   const [currentPassword, setCurrentPassword] = useState("");

   const { token } = useToken();
   const navigate = useNavigate();

   const handleSubmit = async (e: any) => {
      e.preventDefault();

      if (!name || !password || !currentPassword) {
         alert('모든 입력란을 작성해주세요');
         return;
      }

      const data = { name, password, currentPassword };

      try {
         await putWithToken(token, "/api/user", data);

         alert("정보 변경이 완료되었습니다");
         navigate("/");

      } catch (error: any) {
         alert(error.response.data);
      }
   };

   return (
      <div>
         <Header />
         <div className={Styles.main}>
            <div className={Styles.box_container}>
               <div className={Styles.main_text}><p>정보 변경</p></div>
               <div className={Styles.box}>
                  <form onSubmit={handleSubmit}>
                     <div className={Styles.name}>
                        <input
                           type="text"
                           value={name}
                           onChange={(e) => setName(e.target.value)}
                           placeholder="이름"
                        />
                     </div>
                     <div className={Styles.password}>
                        <input
                           type="password"
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}
                           placeholder="새로운 비밀번호"
                        />
                     </div>
                     <div className={Styles.password}>
                        <input
                           type="password"
                           value={currentPassword}
                           onChange={(e) => setCurrentPassword(e.target.value)}
                           placeholder="현재 비밀번호"
                        />
                     </div>
                     <div className={Styles.subbutton}>
                        <button type="submit">완료</button>
                     </div>
                  </form>
               </div>
            </div>
         </div>
      </div>
   );
};

export default ModifyMe;
