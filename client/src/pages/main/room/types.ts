export interface RoomData {
  name: string,
  roomId: string
}

export interface LoaderData {
  room: RoomData
}

export interface MyMessage {
  type: string,
  roomId: string,
  sender: string,
  message: string,
  userCount: number
}
