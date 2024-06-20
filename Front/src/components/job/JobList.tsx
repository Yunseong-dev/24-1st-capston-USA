import { useState, useEffect } from 'react';
import { customAxios } from '../../utils/axios';
import dayjs from 'dayjs';
import { Job } from '../../interface/job';
import { Link } from 'react-router-dom';

const JobPosts: React.FC = () => {
   const [jobPosts, setJobPosts] = useState<Job[]>([]);

   useEffect(() => {
      const fetchJobPosts = async () => {
         try {
            const response = await customAxios.get<Job[]>('/job');
            setJobPosts(response.data);
         } catch (error) {
            alert("게시물을 가져올 수 없습니다")
         }
      };
      fetchJobPosts();
   }, []);

   return (
      <div>
         <a href="/CreateJob">게시물 생성</a>
         <div>
            {jobPosts.map(job => (
               <div>
                  <Link to={`/job/${job.id}`}>
                     <h2>{job.title}</h2>
                     <p>{dayjs(job.createdAt).format("YYYY년 MM월 DD일")}</p>
                  </Link>
               </div>
            ))}
         </div>
      </div>
   );
};

export default JobPosts;
