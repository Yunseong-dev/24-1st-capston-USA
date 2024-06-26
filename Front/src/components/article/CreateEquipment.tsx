import { useEffect, useState } from "react"
import { useNavigate } from 'react-router-dom'
import useToken from "../../hooks/useToken"
import { postWithToken } from "../../utils/axios"


const CreateArticle = () => {
   const [title, setTitle] = useState("")
   const [content, setContent] = useState("")
   const [image, setImage] = useState<File | null>(null)

   const { token } = useToken()

   const navigate = useNavigate()

   useEffect(() => {
      if (!token) {
         navigate("/signin")
         alert("먼저 로그인을 해주세요")
      }
   }, [token, navigate])

   const [imageSrc, setImageSrc]: any = useState(null);

   const onUpload = (e: any) => {
      const file = e.target.files[0];
      setImage(file);
      const reader = new FileReader();
      reader.readAsDataURL(file);

      reader.onload = () => {
         setImageSrc(reader.result);
      };
   }

   const createArticle = async (e: any) => {
      e.preventDefault()

      if (!image) {
         alert("이미지를 선택해주세요")
      }

      const formData = new FormData();
      formData.append('dto', new Blob([JSON.stringify({ title, content })], { type: 'application/json' }));
      if (image) {
         formData.append('image', image);
      }

      try {
         await postWithToken(token, '/articles', formData);

         alert("게시물을 등록했습니다")
         navigate('/equipment')

      } catch (error) {
         console.log(error)
      }
   }

   return (
      <div>
         <form onSubmit={createArticle}>
            <input
               type="text"
               value={title}
               onChange={(e) => setTitle(e.target.value)}
               placeholder="제목"
            />
            <br />
            <textarea
               value={content}
               cols={30}
               rows={10}
               onChange={(e) => setContent(e.target.value)}
            />
            <br />
            <img
               src={imageSrc}
            />
            <br />
            <input
               type="file"
               accept=".png, .jpeg, .jpg"
               onChange={e => onUpload(e)}
               name="image"
            />
            <br />
            <button type="submit" >게시물 등록</button>
         </form>
      </div>
   )
}

export default CreateArticle
