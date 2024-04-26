import { useState } from 'react';
import { customAxios } from "../utils/axios";

const test = () => {
   const [name, setName] = useState("");
   const [phone, setPhone] = useState("");

   const joinHandler = async () => {


      const response = await customAxios.post('/api/user/signup', {
         name,
         phone
      });

   }

   return (
      <div>
         <form onSubmit={joinHandler}>
            <input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="이름" />
            <input type="text" value={phone} onChange={(e) => setPhone(e.target.value)} placeholder="전화번호" />
            <button type="submit">회원가입</button>
         </form>
      </div>
   )
}

export default test