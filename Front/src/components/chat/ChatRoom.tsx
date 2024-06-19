import React, { useState, useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { customAxios, postWithToken } from '../../utils/axios';
import useToken from '../../hooks/useToken';
import { ChatMessage } from '../../interface/chatMessage';

const ChatRoom: React.FC = () => {
    const { chatRoomId } = useParams<{ chatRoomId: string }>();
    const [messages, setMessages] = useState<ChatMessage[]>([]);
    const [newMessage, setNewMessage] = useState('');
    const [webSocket, setWebSocket] = useState<WebSocket | null>(null);
    const messagesEndRef = useRef<HTMLDivElement>(null);
    const { token } = useToken();

    useEffect(() => {
        const ws = new WebSocket(`ws://localhost:8080/chat/${chatRoomId}`);

        ws.onopen = () => {
            console.log('WebSocket 연결 성공');
            setWebSocket(ws);
            fetchMessages();

            const enterMessage = {
                chatRoomId: chatRoomId,
                messageType: "ENTER",
                message: "",
            };
            ws.send(JSON.stringify(enterMessage));
        };

        ws.onmessage = (event) => {
            const message = JSON.parse(event.data) as ChatMessage;
            console.log('New message received:', message);  // 메시지 수신 로그 추가
            setMessages((prevMessages) => [...prevMessages, message]);
            scrollToBottom();
        };

        ws.onclose = () => {
            console.log('WebSocket 연결 종료');
            setWebSocket(null);
        };

        ws.onerror = (error) => {
            console.error('WebSocket 에러:', error);
        };

        return () => {
            ws.close();
        };
    }, [chatRoomId]);

    const fetchMessages = async () => {
        try {
            const response = await customAxios.get(`/chat/messages/${chatRoomId}`);
            setMessages(response.data);
            scrollToBottom();
        } catch (error) {
            console.error('메세지를 가져오는데 실패했습니다', error);
        }
    };

    const sendMessage = async () => {
        if (!webSocket) {
            console.error('WebSocket 연결이 없습니다');
            return;
        }

        if (!newMessage) {
            alert("메세지를 입력해주세요");
            return;
        }

        const messageData = {
            chatRoomId: chatRoomId,
            messageType: "CHAT",
            message: newMessage,
        };

        await postWithToken(token, `/chat/sendMessage/${chatRoomId}`, messageData);

        webSocket.send(JSON.stringify(messageData));
        setNewMessage('');
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNewMessage(event.target.value);
    };

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    return (
        <div>
            <h2>채팅방</h2>
            <div style={{ height: '400px', overflowY: 'scroll' }}>
                {messages.map((message, index) => (
                    <div key={index}>
                        <strong>{message.sender}:</strong>
                        {message.message}
                        <br />
                        <small>{new Date(message.sendAt).toLocaleString()}</small>
                    </div>
                ))}
                <div ref={messagesEndRef}></div>
            </div>
            <input type="text" value={newMessage} onChange={handleInputChange} />
            <button onClick={sendMessage}>전송</button>
        </div>
    );
};

export default ChatRoom;
