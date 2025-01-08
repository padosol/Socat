import { instance } from ".";

export interface ILoginData {
  email: string;
  password: string;
}

export const login = async (loginData: ILoginData) => {
  try {
    const response = await instance.post("/api/user-service/login", loginData);
    return response.data;
  } catch (e) {
    return Promise.reject(e);
  }
};

export interface IJoinData {
  username: string;
  email: string;
  password: string;
}

export const join = async (joinData: IJoinData) => {
  try {
    const response = await instance.post("http://localhost:8000/user-service/users", joinData);

    console.log(response)

    return response.data;
  } catch (e) {
    return Promise.reject(e);
  }
}