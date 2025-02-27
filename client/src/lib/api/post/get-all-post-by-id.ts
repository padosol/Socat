import { instance } from "@/lib/axios/axiosInstance";
import { ApiResponse } from "@/lib/definitions";
import { PostSearch, PostWithPage } from "@/lib/definitions";

export default async function getAllPostByCommunityId(communityId: string, postSearch: PostSearch) {

  try {
    const response = await instance.get<ApiResponse<PostWithPage>>(`/post-service/${communityId}/posts`, {
      params: postSearch
    })

    return response.data.data;
  } catch(e) {
    throw new Error('Failed to fetch post.');
  }

}