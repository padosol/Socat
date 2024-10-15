import instance from "./index";

export function googleLogin() {
  return instance.get("http://localhost:3010/login", {
    withCredentials: true
  });
}