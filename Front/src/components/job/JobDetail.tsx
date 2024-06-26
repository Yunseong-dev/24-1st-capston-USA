import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { customAxios, postWithToken } from "../../utils/axios";
import dayjs from "dayjs";
import useToken from "../../hooks/useToken";
import { Job } from "../../interface/job";

const JobDetail = () => {
   const { id } = useParams<{ id: string }>();
   const [job, setJob] = useState<Job | null>(null);

   const navigate = useNavigate();
   const { token } = useToken();

   useEffect(() => {
      const fetchAJob = async () => {
         try {
            const response = await customAxios.get<Job>(`/jobs/${id}`);
            setJob(response.data);
         } catch (error) {
            console.error(error);
         }
      };
      fetchAJob();
   }, [id]);


   const handleChat = async () => {
      try {
         const response = await postWithToken(token, `/chat/create/job/${id}`, {});

         const roomId = response.data.roomId;
         navigate(`/chat/${roomId}`);
      } catch (error: any) {
         if (error.response && error.response.data) {
            alert(error.response.data)
         }
      }
   };

   if (!job) {
      return <div>Loading...</div>;
   }

   return (
      <div>
         <h2>{job.title}</h2>
         <p>{job.content}</p>
         <p>{dayjs(job.createdAt).format("YYYY년 MM월 DD일")}</p>
         <button onClick={handleChat}>채팅하기</button>
      </div>
   );
};

export default JobDetail;
