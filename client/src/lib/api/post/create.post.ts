"use server"

import { instance } from "@/lib/axios/axiosInstance"
import { redirect } from "next/navigation"
import { revalidatePath } from "next/cache"

export type PostForm = {
  roomId: string,
  title: string,
  content: string,
}

export async function createPost(createPostDto: PostForm) {
  const response =  await instance.post(`/post-service/posts`, createPostDto)
  if (!response.data.success) {
    return response.data;
  }

  revalidatePath(`/socat/${createPostDto.roomId}`)
  redirect(`/socat/${createPostDto.roomId}`)
}