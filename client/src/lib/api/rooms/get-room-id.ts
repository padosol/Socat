import { instance } from "@/lib/axios/axiosInstance";
import { ApiResponse } from "@/lib/definitions";
import { Room } from "@/lib/definitions";

export default async function getRoomById(roomId: string) {

  try {
    const response = await instance.get<ApiResponse<Room>>(`/room-service/rooms/${roomId}`);

    if (response.data.success) {
      return response.data.data;
    }

    return null;
  } catch(error) {
    console.log(error)
    return null;
  }

}