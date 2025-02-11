"use server"

import { instance } from "@/lib/axios/axiosInstance"


export type PostForm = {
  roomId: string,
  title: string,
  content: string,
}

export async function createPost(createPostDto: PostForm) {
  return await instance.post(`/post-service/posts`, createPostDto)
}