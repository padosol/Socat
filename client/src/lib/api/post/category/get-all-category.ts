import { instance } from "@/lib/axios/axiosInstance";


export async function getAllCategory() {
  const response = await instance.get(`/post-service/`)
  console.log(response)
}