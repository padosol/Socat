import {instance} from "./index";

export interface IJoinData {
  username: string,
  email: string,
  password: string
}

export function join(formData: IJoinData) {
  return instance.post("/api/user-service/users", formData)
}

export interface ILoginDTO {
  username: string,
  password: string
}

export function login(data: ILoginDTO) {
  return instance.post("/api/user-service/authenticate", data)
}