import { instance } from "@/lib/axios/axiosInstance";
import { ApiResponse } from "@/lib/definitions";
import { Post } from "@/lib/definitions";

export default async function getAllPostByRoomId(roomId: string) {

  const response = await instance.get<ApiResponse<Post[]>>(`/post-service/${roomId}/posts`, {
    params: {}
  })

  return response.data;
}