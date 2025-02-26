"use server"

import { instance } from "@/lib/axios/axiosInstance";
import { Topic, ApiResponse } from "@/lib/definitions";

export async function getAllTopic() {
    const response = await instance.get<ApiResponse<Topic[]>>(`/community-service/topics`);

    if (response.data.success) {
      return response.data.data;
    }

    return [{topicId: "all", topicName: "전체"}];
} 