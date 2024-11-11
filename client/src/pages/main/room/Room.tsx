import { useEffect, useRef } from "react"
import {
  useLoaderData
} from "react-router-dom"
import * as StompJs from '@stomp/stompjs';

import { getRoom } from "../../../api/room";

export async function loader({params}) {

  const response = await getRoom(params.roomId);

  const room = response.data;

  return {room};
}


const Room = () => {
  const {room} = useLoaderData();

  const client = useRef(null)
  
  const connect = () => {
    console.log("connect")

    client.current = new StompJs.Client({
      brokerURL: "ws://localhost:8000/chat-service/ws-stomp",
      onConnect: () => {
        console.log('success');
        subscribe();
      }
    })

    client.current.activate();
  }

  const subscribe = () => {
    client.current.subscribe(`/sub/chat/room/${room.roomId}`, (body) => {
      console.log(body)
    })
  }

  useEffect(() => {
    connect();
  }, [room])

  return (
    <div>
      {room.name}
    </div>
  )
}

export default Room