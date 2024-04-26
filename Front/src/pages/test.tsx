import { useState } from 'react';
import { customAxios } from "../utils/axios";
import { useNavigate } from 'react-router-dom';

const test = () => {
   const [name, setName] = useState("");
   const [phoneNumber, setPhone] = useState("");

   const navigate = useNavigate();

   const isValidPhoneNumber = (phoneNumber: string) => {
      const regex = /^\d{11}$/
      return regex.test(phoneNumber)
   }

   const joinHandler = async (e: { preventDefault: () => void; }) => {
      e.preventDefault()

      try {
         if (!name || !phoneNumber) {
            alert('모든 입력란을 작성해주세요.')
            return
         }

         if(!isValidPhoneNumber(phoneNumber)){
            alert('올바른 전화번호 형식이 아닙니다.')
            return
         }

         const response = await customAxios.post('/api/user/signup', {
            name,
            phoneNumber
         });

         alert("회원가입에 성공하였습니다")
         navigate('/')

      } catch (error: any) {
         if (error.response.status === 500) {
            alert("이미 존재하는 전화번호 입니다.")
         }
      }
   }

   const checkPhoneHandler = async (e: { preventDefault: () => void; }) => {
      e.preventDefault()

      const response = await customAxios.post('/api/user/SMS', {
         phoneNumber
      });
   }

   return (
      <div>
         <form>
            <input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="이름" />
            <input type="text" value={phoneNumber} onChange={(e) => setPhone(e.target.value)} placeholder="전화번호" />
            <button onClick={checkPhoneHandler}>인증코드 전송</button>
            <button onClick={joinHandler}>회원가입</button>
         </form>
      </div>
   )
}

export default test