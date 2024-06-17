// src/pages/job/JobPosts.tsx

import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { JobPost } from '../../interface/jobPost';
import dayjs from 'dayjs';
import { customAxios } from '../../utils/axios';

const JobPosts: React.FC = () => {
   const [jobPosts, setJobPosts] = useState<JobPost[]>([]);
   const navigate = useNavigate();

   useEffect(() => {
      const fetchJob = async () => {
         try {
            const response = await customAxios.get<JobPost[]>("/job");
            setJobPosts(response.data);
         } catch (error) {
            console.error(error);
         }
      };
      fetchJob();
   }, []);

   return (
      <div>
         <h1>Job Posts</h1>
         <button onClick={() => navigate('/create-jobpost')}>Create Job Post</button>
         <div>
            {jobPosts.map(post => (
               <div key={post.id}>
                  <h2>{post.title}</h2>
                  <p>{post.content}</p>
                  <p>{dayjs(post.createdAt).format("YYYY년 MM월 DD일")}</p>
               </div>
            ))}
         </div>
      </div>
   );
};

export default JobPosts;
