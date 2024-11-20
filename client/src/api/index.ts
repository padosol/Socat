import axios, {AxiosInstance} from 'axios';

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
  (response) => {
    return response
  },
  async (error) => {
    if (error.status == 401) {

      localStorage.removeItem("user-info")

      const accessToken = localStorage.getItem("authorization")
      const refreshToken = localStorage.getItem("refresh_token")

      const response = await instance.post("/api/user-service/refresh-auth", {
        accessToken: accessToken,
        refreshToken: refreshToken
      })

      localStorage.setItem("authorization", response.data.accessToken)
      localStorage.setItem("refresh_token", response.data.refreshToken)

      return;
    }

    return Promise.reject(error)
  }
)

export {instance, authInstance};