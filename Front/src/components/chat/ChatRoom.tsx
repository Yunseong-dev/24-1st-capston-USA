import React, { useState, useEffect, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { postWithToken, fetcherWithToken } from '../../utils/axios';
import useToken from '../../hooks/useToken';
import { ChatMessage } from '../../interface/chatMessage';
import dayjs from 'dayjs';
import styles from "../../css/ChatRoom.module.css"
import profile from "../../assets/profile.png"

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
        const ws = new WebSocket(`ws://married-betti-yunseong-1041f081.koyeb.app/chat/${chatRoomId}`);

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

    const handleInputChange = (e: any) => {
        setNewMessage(e.target.value);
    };

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    const rent = () => {
    }
    return (
        <div className={styles.main}>
            <div className={styles.profile_section}>
                <img src={profile} width="50" />
                <span>장희철</span>
                <button onClick={rent}>대여해주기</button>
            </div>

            <div className={styles.messages}>
                {messages.map((message, index) => (
                    <div
                        key={index}
                        className={styles.message}
                        style={{
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

            <div className={styles.message_input}>
                <form onSubmit={sendMessage}>
                    <input
                        type="text"
                        placeholder="메시지를 입력해주세요."
                        value={newMessage}
                        onChange={handleInputChange}
                    />
                    <button type="submit">⬆</button>
                </form>
            </div>
        </div>
    );
};

export default ChatRoom;
