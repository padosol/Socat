import { useEffect, useRef } from "react"
import {
  useLoaderData
} from "react-router-dom"
import { Stomp } from "@stomp/stompjs";

import { getRoom } from "../../../api/room";

export async function loader({params}) {

  const response = await getRoom(params.roomId);

  console.log(response)

  return null;
}


const Room = () => {
  const stompClient = useRef(null)
  
  const connect = () => {
    const socket = new WebSocket("ws://localhost:8000/ws-stomp");
    stompClient.current = Stomp.over(socket)
    stompClient.current.connect({}, () => {
      stompClient.current.subscribe(`/sub/chat/room`)
    })
  }

  useEffect(() => {
    connect();
  }, [])

  return (
    <div>
      방
    </div>
  )
}

export default Room