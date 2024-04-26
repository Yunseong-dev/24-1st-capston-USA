import { useState } from 'react';
import { customAxios } from "../utils/axios";
import { useNavigate } from 'react-router-dom';

const test = () => {
   const [name, setName] = useState("");
   const [phoneNumber, setPhone] = useState("");

   const navigate = useNavigate();

   const joinHandler = async (e: { preventDefault: () => void; }) => {
      e.preventDefault()

      try {
         const response = await customAxios.post('/api/user/signup', {
            name,
            phoneNumber
         });
         if (response.status === 200) {
            alert("회원가입에 성공하였습니다")
            navigate('')
         } else {
            alert('가입은 성공적으로 되었으나 상태 코드가 200이 아닙니다.');
         }
      } catch (error: any) {
         if (error.response.status === 500) {
            alert("이미 존재하는 전화번호 입니다.")
         }
      }
   }

   return (
      <div>
         <form onSubmit={joinHandler}>
            <input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="이름" />
            <input type="text" value={phoneNumber} onChange={(e) => setPhone(e.target.value)} placeholder="전화번호" />
            <button type="submit">회원가입</button>
         </form>
      </div>
   )
}

export default test