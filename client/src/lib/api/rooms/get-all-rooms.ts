import { instance } from "@/lib/axios/axiosInstance";
import { ApiResponse } from "@/lib/definitions";
import { Room } from "@/lib/definitions";

export async function fetchRooms(type: string) {
  const response = await instance.get<ApiResponse<Room[]>>(`/room-service/rooms?type=${type}`)
  return response.data.data;
}