import {authInstance} from './index'

export interface IUserInfoResponse {
  email: string;
  id: string;
  userName: string;
}

export function getUserInfo() {
  return authInstance.get<IUserInfoResponse>("/api/user-service/users")
}