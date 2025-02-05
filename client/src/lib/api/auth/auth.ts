"use server"

import { instance } from "@/lib/axios/axiosInstance"
import { ApiResponse, TokenDTO } from "@/lib/definitions";
import { cookies } from 'next/headers'

export async function authenticate() {
  try {
    const response = await instance.post("http://localhost:8000/user-service/auth/accessToken");

    if (response.data.success) {
      const activeStatus = response.data.data;
  
      return activeStatus;
    } 

    return false;
  } catch(e) {
    console.error(e);
    return false;
  }
}

export async function refresh() {

  try {
    const cookieStore = await cookies();
    const accessToken = cookieStore.get("accessToken");
    const refreshToken = cookieStore.get("refreshToken");

    const response = await instance.post<ApiResponse<TokenDTO>>(`http://localhost:8000/user-service/refresh-auth`, {
      accessToken: accessToken?.value || "", 
      refreshToken: refreshToken?.value || "",
    })
    
    if (response.data.success) {
      cookieStore.set("accessToken", response.data.data.accessToken);
      cookieStore.set("refreshToken", response.data.data.refreshToken);

      return true;
    }


    return false;
  } catch(e) {
    console.error(e);
    return false;
  }
}