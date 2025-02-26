import { instance } from "@/lib/axios/axiosInstance";
import { ApiResponse } from "@/lib/definitions";
import { Community } from "@/lib/definitions";

export async function fetchCommunities(type: string) {
  const response = await instance.get<ApiResponse<Community[]>>(`/community-service/communities?type=${type}`)

  if (response.data.success) {
    return response.data.data;
  }

  return null;
}