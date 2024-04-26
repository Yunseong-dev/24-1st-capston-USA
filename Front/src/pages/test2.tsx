import { useEffect, useState } from "react";
import { customAxios } from "../utils/axios";
import { useNavigate } from 'react-router-dom';
import useToken from "../hooks/useToken";
import axios from 'axios';


const test2 = () => {
   const [name, setName] = useState("");
   const [phoneNumber, setPhone] = useState("");

   const { token, setToken } = useToken();
   const navigate = useNavigate();

   useEffect(() => {
      if (!!token) {
         alert("이미 로그인이 되어있습니다.");
         navigate("/");
      }
   }, [token, navigate]);

   const loginHandler = async (e: { preventDefault: () => void; }) => {
      e.preventDefault();

      if (!name || !phoneNumber) {
         alert("입력창을 모두 입력해주세요");
         return;
      }

      try {
         const response = await customAxios.post('/api/user/signin', {
            name,
            phoneNumber
         });

         const token = response.data.token;
         setToken(token);
         navigate("/");
      } catch (error) {
         console.error("로그인 중 오류 발생", error);
         if (axios.isAxiosError(error)) {
            const response = error.response;
            if (response?.status === 404) {
               alert("로그인 중 알 수 없는 오류가 발생했습니다.");
            } else {
               alert("아이디 또는 비밀번호가 틀렸습니다.");
            }
         }
      }
   };

   return (
      <div>
         <form>
            <input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="이름" />
            <input type="text" value={phoneNumber} onChange={(e) => setPhone(e.target.value)} placeholder="전화번호" />
            <button onClick={loginHandler}>로그인</button>
         </form>
      </div>
   )
}

export default test2