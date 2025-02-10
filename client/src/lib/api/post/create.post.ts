"use server"

import { instance } from "@/lib/axios/axiosInstance"

export type PostForm = {
  roomId: string,
  title: string,
  content: string,
}

export async function createPost(createPostDto: PostForm) {

  const response = await instance.post(`/post-service/posts`, createPostDto)

  if (response.data.success) {
    return response.data.data;
  } else {
    throw new Error("게시글 등록 실패")
  }

}