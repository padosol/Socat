import {instance} from ".";

export function getRooms() {
  return instance.get("/api/chat-service/chat/rooms")
}

interface RoomData {
  name: string,
  roomId: string
}

export function getRoom(roomId: string) {
  return instance.get(`/api/chat-service/chat/rooms/${roomId}`)
}
