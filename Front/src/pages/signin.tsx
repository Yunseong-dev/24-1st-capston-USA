import { useEffect, useState } from "react"
import { customAxios } from "../utils/axios"
import { useNavigate } from 'react-router-dom'
import useToken from "../hooks/useToken"

const signin = () => {
   const [phoneNumber, setPhone] = useState("")
   const [password, setPassword] = useState("")

   const navigate = useNavigate()

   const { token, setToken } = useToken()

   useEffect(() => {
      if (!!token) {
         alert("이미 로그인이 되어있습니다.")
         navigate("/")
      }
   }, [token, navigate])

   const joinHandler = async (e: { preventDefault: () => void }) => {
      e.preventDefault()
      
      try {
         if (!phoneNumber || !password) {
            alert('모든 입력란을 작성해주세요')
            return
         }

         const response = await customAxios.post('/api/user/signin', {
            phoneNumber,
            password
         })

         const token = response.data.token
         setToken(token);
         navigate("/")
         alert("로그인이 완료되었습니다")

      } catch (error: any) {
         if (error.response && error.response.data) {
            alert("오류가 발생했습니다")
         }
      }
   }

   return (
      <div>
         <form>
            <input type="text" value={phoneNumber} onChange={(e) => setPhone(e.target.value)} placeholder="전화번호" />
            <input type="text" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="비밀번호" />
            <button onClick={joinHandler}>로그인</button>
         </form>
      </div>
   )
}

export default signin