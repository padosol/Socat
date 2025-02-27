import { instance } from "@/lib/axios/axiosInstance";
import { ApiResponse } from "@/lib/definitions";
import { Community } from "@/lib/definitions";

export default async function getCommunityById(communityId: string) {

  try {
    const response = await instance.get<ApiResponse<Community>>(`/community-service/communities/${communityId}`);

    if (response.data.success) {
      return response.data.data;
    }

    return null;
  } catch(error) {
    console.log(error)
    return null;
  }

}