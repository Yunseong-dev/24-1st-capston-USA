import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { JobPost } from '../../interface/jobPost';
import dayjs from 'dayjs';
import { customAxios, postWithToken } from '../../utils/axios';
import useToken from '../../hooks/useToken';

const JobPosts: React.FC = () => {
   const [jobPosts, setJobPosts] = useState<JobPost[]>([]);
   const navigate = useNavigate();
   const { token } = useToken();

   useEffect(() => {
      const fetchJobPosts = async () => {
         try {
            const response = await customAxios.get<JobPost[]>('/job');
            setJobPosts(response.data);
         } catch (error) {
            alert("게시물을 가져올 수 없습니다")
         }
      };
      fetchJobPosts();
   }, []);

   const handleChat = async (jobId: number) => {
      try {
         const response = await postWithToken(token, `/chat/create/${jobId}`, "");

         const roomId = response.data.roomId;
         navigate(`/chat/${roomId}`);
      } catch (error: any) {
         if (error.response && error.response.data) {
            alert(error.response.data)
         }
      }
   };

   return (
      <div>
         <h1>Job Posts</h1>
         <button onClick={() => navigate('/CreateJobpost')}>Create Job Post</button>
         <div>
            {jobPosts.map(post => (
               <div key={post.id}>
                  <h2>{post.title}</h2>
                  <p>{post.content}</p>
                  <p>{dayjs(post.createdAt).format('YYYY년 MM월 DD일')}</p>
                  <button onClick={() => handleChat(post.id)}>채팅하기</button>
               </div>
            ))}
         </div>
      </div>
   );
};

export default JobPosts;
