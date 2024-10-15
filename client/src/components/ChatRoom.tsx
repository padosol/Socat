import React, { useEffect, useState } from 'react';
import {io, Socket} from 'socket.io-client';

interface Message {
  text: string;
  sender: 'user' | 'other';
}

const ChatRoom = ({roomId}: {roomId: string}) => {

  const [socket, setSocket] = useState<Socket | null>(null);
  const [messages, setMessages] = useState<Message[]>([]);
  const [inputMessage, setInputMessage] = useState<string>('');

  const handleSendMessage = (): void => {
    if (inputMessage.trim() !== '') {
      socket?.emit("chat", {text: inputMessage, sender: "user", roomId: roomId})
      setInputMessage('');
    }
  };

  useEffect(() => {
    if(!socket) {
      setSocket(io('localhost:3010/chat'));

      // 메시지 초기화
      setMessages([]);
    } else {
      socket.emit('welcome', {name: "tester", roomId: roomId});
  
      socket.on("welcomeRoom", (msg) => {
        setMessages(messages => [...messages, { text: msg + "방에 오신걸 환영합니다.", sender: 'user' }]);
      })

      socket.on("newUserEnter", (msg) => {
        setMessages(messages => [...messages, { text: msg + "님이 입장하셨습니다.", sender: "other"}]);
      })

      socket.on("chat", (msg) => {
        console.log(msg)
        setMessages(messages => [...messages, { text: msg, sender: "other"}]);
      })
    }
  }, [socket])

  useEffect(() => {
    console.log(messages);
  }, [messages])

  return (
    <div>
      <div>{roomId}번방</div>
      <div className="flex flex-col h-[500px] bg-gray-100 mt-5">
        <div className="flex-1 overflow-y-auto p-4 space-y-4">
          {messages.map((message, index) => ( 
            <div
              key={index}
              className={`flex ${
                message.sender === 'user' ? 'justify-end' : 'justify-start'
              }`}
            >
              <div
                className={`max-w-xs px-4 py-2 rounded-lg ${
                  message.sender === 'user'
                    ? 'bg-blue-500 text-white'
                    : 'bg-gray-300 text-gray-800'
                }`}
              >
                {message.text}
              </div>
            </div>
          ))}
        </div>
        <div className="bg-white p-4 border-t">
          <div className="flex space-x-2">
            <input
              type="text"
              value={inputMessage}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => setInputMessage(e.target.value)}
              onKeyPress={(e: React.KeyboardEvent<HTMLInputElement>) => e.key === 'Enter' && handleSendMessage()}
              className="flex-1 border rounded-full px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="메시지를 입력하세요..."
            />
            <button
              onClick={handleSendMessage}
              className="bg-blue-500 text-white rounded-full p-2 hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <button>보내기</button>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatRoom;