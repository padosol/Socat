import instance from ".";

export function createRoom(param) {
  return instance.post('/rooms', param)
}
