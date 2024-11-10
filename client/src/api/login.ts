import {instance} from "./index";

export function googleLogin() {
  return instance.get("http://localhost:3010/login", {
    withCredentials: true
  });
}

interface JoinData {
  username: string,
  email: string,
  password: string
}

export function join(formData: JoinData) {
  return instance.post("/api/user-service/users", formData)
}


interface LoginDTO {
  username: string,
  password: string
}
export function login(data: LoginDTO) {
  return instance.post("/api/user-service/login", data)
}