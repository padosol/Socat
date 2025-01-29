import axios, { AxiosInstance } from "axios";
import { cookies } from "next/headers";

const instance: AxiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 30000, // 30 초
})


instance.interceptors.request.use(
  async (config) => {
    const cookieStore = await cookies();
    const accessToken = cookieStore.get("accessToken");

    if (!accessToken) return config;

    config.headers.Authorization = `Bearer ${accessToken.value}`;
    return config;
  },
  (error) => Promise.reject(error),
);

instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // 401 (Unauthorized) 체크 and refresh token 이미 시도했는지 체크
    // if (error.response?.status === 401 && !originalRequest._retry) {
    //   originalRequest._retry = true;

    //   const cookie = await cookies();
    //   try {
    //     // refresh token 시도

    //     const refreshToken = cookie.get("refreshToken");
    //     const { data } = await instance.post("/user-service/auth/refresh-token", {
    //       refreshToken,
    //     });

    //     console.log(data)

    //     const newAccessToken = data.accessToken;

    //     // 토큰 갱신
    //     cookie.set("accessToken", newAccessToken);

    //     // 갱신된 토큰으로 재시도
    //     instance.defaults.headers.common.Authorization = `Bearer ${newAccessToken}`;
    //     originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;

    //     return await instance(originalRequest);
    //   } catch (refreshError) {
    //     cookie.delete("refreshToken");
    //     cookie.delete("accessToken");
        
    //     return Promise.reject(refreshError);
    //   }
    // }

    return Promise.reject(error);
  },
);

export { instance };