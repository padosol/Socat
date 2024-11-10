import {instance} from ".";

export function getRooms() {
  return instance.get("/api/chat-service/chat/rooms")
}
