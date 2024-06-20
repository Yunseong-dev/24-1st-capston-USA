import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { customAxios } from "../../utils/axios";
import dayjs from "dayjs";
import { Job } from "../../interface/job";

const JobDetail = () => {
   const { id } = useParams<{ id: string }>();
   const [job, setJob] = useState<Job | null>(null);

   useEffect(() => {
      const fetchAJob = async () => {
         try {
            const response = await customAxios.get<Job>(`/articles/${id}`);
            setJob(response.data);
         } catch (error) {
            console.error(error);
         }
      };
      fetchAJob();
   }, [id]);

   if (!job) {
      return <div>Loading...</div>;
   }

   return (
      <div>
         <h2>{job.title}</h2>
         <p>{job.content}</p>
         <p>{dayjs(job.createdAt).format("YYYY년 MM월 DD일")}</p>
      </div>
   );
};

export default JobDetail;
