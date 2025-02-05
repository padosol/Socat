"use server"

import { instance } from "@/lib/axios/axiosInstance";
import { cookies } from "next/headers";

export async function logout() {
  const cookieStore = await cookies();
  const accessToken = cookieStore.get("accessToken")
  const refreshToken = cookieStore.get("refreshToken")

  await instance.post(`http://localhost:8000/user-service/logout`, {
    accessToken: accessToken?.value, 
    refreshToken: refreshToken?.value
  })

  cookieStore.delete("accessToken")
  cookieStore.delete("refreshToken")
}