import {instance, authInstance} from ".";

export function getRooms() {
  return instance.get("/api/room-service/rooms")
}

interface RoomData {
  name: string,
  roomId: string
}

export function getRoom(roomId: string) {
  return instance.get(`/api/room-service/rooms/${roomId}`)
}

export function createRoom(roomName: string) {
  return authInstance.post(`/api/room-service/rooms`, {
    roomName
  })
}