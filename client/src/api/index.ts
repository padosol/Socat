import axios, {AxiosInstance} from 'axios';
import { IErrorResponse } from './type/error';

const instance: AxiosInstance = axios.create({
  baseURL: "/",
  headers: {
    "Cache-Control": "no-cache",
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
  }
});

const authInstance: AxiosInstance = axios.create({
  baseURL: "/",
  headers: {
    "Cache-Control": "no-cache",
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
  }
})

authInstance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("authorization");

    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

authInstance.interceptors.response.use(
  (response) => response,
  async (error) => {

    const originalRequest = error.config;

    if (
      error.response &&
      error.response.status === 401 &&
      !originalRequest._retry
    ) {
      originalRequest._retry = true

      localStorage.removeItem("user-info")

      const accessToken = localStorage.getItem("authorization")
      const refreshToken = localStorage.getItem("refresh_token")

      try {
          const response = await instance.post("/api/user-service/refresh-auth", {
            accessToken: accessToken,
            refreshToken: refreshToken
          })

          if (response.data) {
            localStorage.setItem("authorization", response.data.accessToken)
            localStorage.setItem("refresh_token", response.data.refreshToken)

            return authInstance(originalRequest);
          }

      } catch(e) {
        return Promise.reject(e)
      }

    } else {
      return Promise.reject(error)

    }

  }
)

export {instance, authInstance};