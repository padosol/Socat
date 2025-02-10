import { instance } from "@/lib/axios/axiosInstance";
import { ApiResponse } from "@/lib/definitions";
import { Post, PostSearch } from "@/lib/definitions";

export default async function getAllPostByRoomId(roomId: string, postSearch: PostSearch) {
  const response = await instance.get<ApiResponse<Post[]>>(`/post-service/${roomId}/posts`, {
    params: postSearch
  })

  console.log(response)

  return response.data.data;
}