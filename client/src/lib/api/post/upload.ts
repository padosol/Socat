"use server"

import { instance } from "@/lib/axios/axiosInstance"

export async function uploadFile(file: FormData) {

  const response = await instance.post("/post-service/upload", file, {
    headers: {
      "Content-Type": "multipart/form-data"
    }
  })

  if (response.data.success) {
    return response.data.data;
  }

  return null
}