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
         navigate("/")
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

   const createArticle = async (e: { preventDefault: () => void }) => {
      e.preventDefault()

      const formData = new FormData();
      formData.append('dto', new Blob([JSON.stringify({ title, content })], { type: 'application/json' }));
      if (image) {
         formData.append('image', image);
      }

      try {
         await postWithToken(token, '/articles/create', formData);

         navigate('/ArticleList')
         alert("게시물을 등록했습니다")

      } catch (error: any) {
         if (error.response && error.response.data) {
            alert(error.response.data)
         }
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
               alt="이미지 미리보기"
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
