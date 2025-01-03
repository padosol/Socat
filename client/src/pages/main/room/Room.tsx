import React, { useEffect, useRef, useState } from "react"
import {
  Link,
  useLoaderData,
  LoaderFunctionArgs
} from "react-router-dom"
import * as StompJs from '@stomp/stompjs';
import { getRoom } from "../../../api/room";
import { getUserInfo } from "../../../api/user.ts";
import { IUserInfo } from "../../../types/user.ts";

import {
  LoaderData,
  MyMessage
} from './types.ts'

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

  const inputRef = useRef<HTMLInputElement | null>(null);

  const handleMessageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(e.target.value)
  }

  const connect = () => {
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
          
          // 유저 정보가 있는지 확인
          const stringUserInfo = localStorage.getItem("user-info");

          if (stringUserInfo) {
            const userInfo: IUserInfo = JSON.parse(stringUserInfo);

            wsClient.current.publish({
              destination: "/pub/chat/message",
              headers: {Authorization: window.localStorage.getItem('authorization')!},
              body: JSON.stringify({type: "JOIN", roomId: room.roomId, sender: userInfo?.userName})
            })
          }

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
          headers: {Authorization: window.localStorage.getItem('authorization')!},
          body: JSON.stringify({type: "CHAT", roomId: room.roomId, sender: "tester", message: message})
        })
        setMessage("")
      }
    }
  }

  const handleChatFocus = async () => {
      try {
        await getUserInfo();
      } catch(e) {
        console.log(e)
        alert("로그인 후 채팅 가능합니다.")
        if (inputRef.current) {
          inputRef.current.blur();
        } 
      }
  }

  useEffect(() => {
    connect();

    return () => {
      setMessages([])

      if(wsClient.current) {
        wsClient.current.deactivate();
      }
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

      <div className="border  rounded-xl h-[700px] w-[400px] p-5 shadow-lg">
        <div className="h-[600px] mb-3 overflow-y-auto ">
          {messages.length ? 
            <div>
              {messages.map( (message, index) => (
                message.type == 'JOIN' 
                ? <div key={index} className="mb-1 p-2">{message.message}</div>
                : <div key={index}
                    className="bg-slate-100 mb-1 p-2 rounded-lg"
                  >
                    <span>{message.sender}</span>: {message.message}
                  </div>
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
            onFocus={handleChatFocus}
            onKeyDown={sendMessage}
            ref={inputRef}
          ></input>
        </div>
      </div>
    </div>
  )
}

export default Room