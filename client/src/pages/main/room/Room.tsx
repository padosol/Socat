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

  const wsClient = useRef<StompJs.Client | null>(null);
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState<MyMessage[]>([]);

  const handleMessageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(e.target.value)
  }

  const connect = () => {
    // const client = new WebSocket("ws://localhost:8000/chat-service/ws-stomp")
    // wsClient.current = StompJs.Stomp.over(client);
    wsClient.current = new StompJs.Client({
      brokerURL: "ws://localhost:8000/chat-service/ws-stomp",
      connectHeaders: { // 이 부분 새로 추가
        Authorization: window.localStorage.getItem('authorization')!,
      },
      onConnect: () => {
        if(wsClient.current) {
          wsClient.current.subscribe(`/sub/chat/room/${room.roomId}`, (message: StompJs.IMessage) => {
            const newMessage = JSON.parse(message.body);
    
            setMessages((messages) => [...messages, newMessage])
    
          })

          wsClient.current.publish({
            destination: "/pub/chat/message",
            body: JSON.stringify({type: "JOIN", roomId: room.roomId, sender: "tester"})
          })
        }

      }
    })

    wsClient.current.activate();
  }

  const sendMessage = (e: React.KeyboardEvent) => {

    if(e.key != "Enter") {
      return;
    }

    if(message) {
      if(wsClient.current) {
        wsClient.current.publish({
          destination: "/pub/chat/message",
          body: JSON.stringify({type: "CHAT", roomId: room.roomId, sender: "tester", message: message})
        })
        setMessage("")
      }
    }
  }

  const disconnect = () => {
    if(wsClient.current) {
      // wsClient.current.send("/pub/chat/message", {}, JSON.stringify({type: "LEAVE", roomId: room.roomId, sender: "tester"}))
      wsClient.current.deactivate();
    }
  }

  useEffect(() => {
    connect();

    return () => {
      setMessages([])
      disconnect();
    }
  }, [room])


  return (
    <div>
      <div className="flex">
        <button className="px-1 hover:scale-150 mb-2">
          <Link to={"/"}>
            <img src="/src/assets/arrow_left.svg" />
          </Link>
        </button>
        <div className="ml-2">
          {room.name}
        </div>
      </div>

      <div className="border border-black rounded-xl h-[500px] w-[400px] p-5">
        <div className="h-[400px] border mb-3 overflow-y-auto ">
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
        <div className="">
          <input 
            className="border bg-slate-100 rounded-lg p-2 w-full" 
            onChange={handleMessageChange} 
            value={message}
            placeholder="채팅에 참여하려면 로그인을 해주세요."
            onKeyDown={sendMessage}
          ></input>
        </div>
      </div>
    </div>
  )
}

export default Room