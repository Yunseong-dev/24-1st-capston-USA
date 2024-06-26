import React, { useState, useEffect, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { postWithToken, fetcherWithToken } from '../../utils/axios';
import useToken from '../../hooks/useToken';
import { ChatMessage } from '../../interface/chatMessage';
import dayjs from 'dayjs';

const ChatRoom: React.FC = () => {
    const { chatRoomId } = useParams<{ chatRoomId: string }>();
    const [messages, setMessages] = useState<ChatMessage[]>([]);
    const [newMessage, setNewMessage] = useState('');
    const [user, setUser] = useState('');
    const [webSocket, setWebSocket] = useState<WebSocket | null>(null);
    const messagesEndRef = useRef<HTMLDivElement>(null);
    
    const { token } = useToken();

    const navigate = useNavigate();

    useEffect(() => {
        if (!token) {
            navigate("/signin")
            alert("먼저 로그인을 해주세요")
        }
    }, [token, navigate])

    useEffect(() => {
        const ws = new WebSocket(`ws://ec2-43-203-248-226.ap-northeast-2.compute.amazonaws.com:8080/chat/${chatRoomId}`);

        ws.onopen = () => {
            console.log('WebSocket 연결 성공');
            setWebSocket(ws);
            fetchMessages();
            fetchUser();
        };

        ws.onmessage = (event) => {
            const receivedMessage = JSON.parse(event.data);
            setMessages(prevMessages => [...prevMessages, receivedMessage]);
        };

        ws.onclose = () => {
            console.log('WebSocket 연결 종료');
            setWebSocket(null);
        };

        ws.onerror = () => {
            console.error('WebSocket 에러');
        };

        return () => {
            ws.close();
        };
    }, [chatRoomId]);

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    const fetchMessages = async () => {
        try {
            const response = await fetcherWithToken(token, `/chat/messages/${chatRoomId}`);
            setMessages(response.data);
        } catch (error) {
            console.error('메세지를 가져오는데 실패했습니다');
        }
    };

    const fetchUser = async () => {
        try {
            const response = await fetcherWithToken(token, `/chat/getUser`);
            setUser(response.data.name);
        } catch (error) {
            console.error('유저를 가져오는데 실패했습니다');
        }
    };

    const sendMessage = async (e: any) => {
        e.preventDefault();
        if (!webSocket) {
            console.error('WebSocket 연결이 없습니다');
            return;
        }

        if (!newMessage) {
            alert('메세지를 입력해주세요');
            return;
        }

        const messageData = {
            chatRoomId: chatRoomId,
            sender: user,
            messageType: 'CHAT',
            message: newMessage,
            sendAt: new Date().toISOString(),
        };

        try {
            await postWithToken(token, '/chat/sendMessage', messageData);
            setNewMessage('');
        } catch (error) {
            console.error('메세지를 보내는데 실패했습니다', error);
        }

        webSocket.send(JSON.stringify(messageData));
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNewMessage(event.target.value);
    };

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    return (
        <div style={{ fontFamily: 'Arial, sans-serif' }}>
            <h2>채팅방</h2>
            <div style={{ height: '400px', overflowY: 'scroll', border: '1px solid #ccc', borderRadius: '5px', padding: '10px', marginBottom: '10px' }}>
                {messages.map((message, index) => (
                    <div
                        key={index}
                        style={{
                            marginBottom: '10px',
                            padding: '5px',
                            borderRadius: '5px',
                            maxWidth: '70%',
                            alignSelf: message.sender === user ? 'flex-end' : 'flex-start',
                            backgroundColor: message.sender === user ? '#e6f2ff' : '#f0f0f0',
                        }}
                    >
                        <strong>{message.sender}: </strong> {message.message}
                        <br />
                        <small>{dayjs(message.sendAt).format('DD일 hh시 mm분 ss초')}</small>
                    </div>
                ))}
                <div ref={messagesEndRef}></div>
            </div>
            <form onSubmit={sendMessage}>
                <input type="text" value={newMessage} onChange={handleInputChange} style={{ marginRight: '10px' }} />
                <button type="submit">전송</button>
            </form>
        </div>
    );
};

export default ChatRoom;
