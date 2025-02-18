import { instance } from "@/lib/axios/axiosInstance";
import { Post, ApiResponse } from "@/lib/definitions";

export async function getPostById(postId: string) {
  const response = await instance.get<ApiResponse<Post>>(`/post-service/posts/${postId}`)

  if (response.data.success) {
    return response.data.data;
  }

  return null;
} 