import {instance} from ".";

export function getRooms() {
  return instance.get("/api/chat-service/chat/rooms")
}

export function getRoom(roomId) {
  return instance.get(`/api/chat-service/chat/rooms/${roomId}`)
}
