import { useEffect, useState } from 'react';
import { fetcherWithToken } from '../../utils/axios';
import useToken from '../../hooks/useToken';
import { Chat } from '../../interface/Chat';
import { Link, useNavigate } from 'react-router-dom';

const ChatList = () => {
   const [chatRooms, setChatRooms] = useState<Chat[]>([]);

   const { token } = useToken();

   const navigate = useNavigate();

   useEffect(() => {
      if (!token) {
         navigate("/signin")
         alert("먼저 로그인을 해주세요")
      }
   }, [token, navigate])

   useEffect(() => {
      const fetchChatRooms = async () => {
         try {
            const response = await fetcherWithToken(token, '/chat/myRooms');
            setChatRooms(response.data);
         } catch (error) {
            console.error('채팅방 목록을 가져오는데 실패했습니다.', error);
         }
      };

      if (token) {
         fetchChatRooms();
      }
   }, [token]);

   return (
      <div>
         <h2>나의 채팅방 목록</h2>
         {chatRooms.length > 0 ? (
            <ul>
               {chatRooms.map((room) => (
                  <li key={room.roomId}>
                     <Link to={`/chat/${room.roomId}`}>{room.jobTitle}</Link>
                  </li>
               ))}
            </ul>
         ) : (
            <p>채팅방이 없습니다.</p>
         )}
      </div>
   );
};

export default ChatList;
