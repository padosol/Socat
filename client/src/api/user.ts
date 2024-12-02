import {authInstance} from './index'

export function getUserInfo() {
  return authInstance.get("/api/user-service/users")
}