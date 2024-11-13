import React, { useEffect, useRef, useState } from "react"
import {
  Link,
  useLoaderData,
  LoaderFunctionArgs
} from "react-router-dom"
import * as StompJs from '@stomp/stompjs';

import { getRoom } from "../../../api/room";

interface RoomData {
  name: string,
  roomId: string
}

interface LoaderData {
  room: RoomData
}

interface MyMessage {
  type: string,
  roomId: string,
  sender: string,
  message: string,
  userCount: number
}

export async function loader({params}: LoaderFunctionArgs ) {
  const roomId= params.roomId
  if (!roomId) {
    return {};
  }

  const response = await getRoom(roomId);
  const room = response.data;
  return {room};
}

const Room = () => {
  const {room} = useLoaderData() as LoaderData;

  const wsClient = useRef<StompJs.CompatClient | null>(null);
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState<MyMessage[]>([]);

  const handleMessageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(e.target.value)
  }

  const connect = () => {
    const client = new WebSocket("ws://localhost:8000/chat-service/ws-stomp")
    wsClient.current = StompJs.Stomp.over(client);

    wsClient.current.connect({}, () => {

      if(wsClient.current) {
        wsClient.current.subscribe(`/sub/chat/room/${room.roomId}`, (message: StompJs.IMessage) => {
          const newMessage = JSON.parse(message.body);
  
          setMessages((messages) => [...messages, newMessage])
  
        })
  
        wsClient.current.send("/pub/chat/message", {}, JSON.stringify({type: "JOIN", roomId: room.roomId, sender: "tester"}))
      }

    })
  }

  const sendMessage = () => {
    if(message) {
      if(wsClient.current) {
        wsClient.current.send("/pub/chat/message", {}, JSON.stringify({type: "CHAT", roomId: room.roomId, sender: "tester", message: message}))
        setMessage("")
      }
    }
  }

  const disconnect = () => {
    if(wsClient.current) {
      wsClient.current.send("/pub/chat/message", {}, JSON.stringify({type: "LEAVE", roomId: room.roomId, sender: "tester"}))
      wsClient.current.disconnect();
    }
  }

  useEffect(() => {
    connect();

    return () => {
      setMessages([]);
      disconnect();
    };
  }, [room])


  return (
    <div>
      <div>
        <button className="border px-1 hover:bg-slate-200 mb-2">
          <Link to={"/"}>
            나가기
          </Link>
        </button>
      </div>
      <div>
        {room.name}
      </div>
      <div>
        {messages.length ? 
          <div>
            {messages.map( (message, index) => (
              <div key={index}>{message.message}</div>
            ))}
          </div>
          :
          <div>
          </div>
        }
      </div>
      <div className="mt-60">
        <input 
          className="border border-black p-1" 
          onChange={handleMessageChange} 
          value={message}
          placeholder="채팅에 참여하려면 로그인을 해주세요."
        ></input>
        <button className="ml-2 border border-black rounded-md hover:bg-slate-300 p-1" onClick={sendMessage}>전송</button>
      </div>
    </div>
  )
}

export default Room