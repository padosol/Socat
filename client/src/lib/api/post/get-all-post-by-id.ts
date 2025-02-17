import { instance } from "@/lib/axios/axiosInstance";
import { ApiResponse } from "@/lib/definitions";
import { PostSearch, PostWithPage } from "@/lib/definitions";

export default async function getAllPostByRoomId(roomId: string, postSearch: PostSearch) {
  const response = await instance.get<ApiResponse<PostWithPage>>(`/post-service/${roomId}/posts`, {
    params: postSearch
  })

  return response.data.data;
}