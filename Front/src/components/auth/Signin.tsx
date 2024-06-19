import { useEffect, useState } from "react"
import { customAxios } from "../../utils/axios"
import { useNavigate } from 'react-router-dom'
import useToken from "../../hooks/useToken"

const signin = () => {
   const [phoneNumber, setPhone] = useState("")
   const [password, setPassword] = useState("")

   const navigate = useNavigate()

   const { token, setToken } = useToken()

   useEffect(() => {
      if (!!token) {
         navigate("/")
         alert("이미 로그인이 되어있습니다")
      }
   }, [token, navigate])

   const signin = async (e: { preventDefault: () => void }) => {
      e.preventDefault()

      try {
         if (!phoneNumber || !password) {
            alert('모든 입력란을 작성해주세요')
            return
         }

         if (password.length > 4) {
            alert('비밀번호는 4자리 이상 입력해주세요')
            return
         }

         const response = await customAxios.post('/api/user/signin', {
            phoneNumber,
            password
         })

         setToken(response.data.token);
         navigate("/")
         alert("로그인이 완료되었습니다")

      } catch (error: any) {
         if (error.response && error.response.data) {
            alert(error.response.data)
         }
      }
   }

   return (
      <div>
         <form onSubmit={signin}>
            <input type="text" value={phoneNumber} onChange={(e) => setPhone(e.target.value)} placeholder="전화번호" />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="비밀번호" />
            <button type="submit">로그인</button>
         </form>
      </div>
   )
}

export default signin