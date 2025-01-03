import { 
  Form,
  Link,
  redirect
} from "react-router-dom";

import { login, ILoginDTO } from "../../api/login";
import { getUserInfo, IUserInfoResponse } from "../../api/user";
import useUserStore from "../../store/userInfo";

export async function action(
  { request }: {request: Request}
) {
  const formData = await request.formData();

  const loginDTO: ILoginDTO = {
    email: formData.get("email") as string,
    password: formData.get("password") as string
  } 

  try {
    const response = await login(loginDTO);

    if (response.status === 200) {
      //header 에 token 추가
      const access_token  = response.data.accessToken
      const refresh_token = response.data.refreshToken

      localStorage.setItem("authorization", access_token)
      localStorage.setItem("refresh_token", refresh_token)

      const userResponse = await getUserInfo();

      const setUserInfo = useUserStore.getState().setUserInfo;
      setUserInfo(userResponse.data);

      if(userResponse.status === 200) {
        localStorage.setItem("user-info", JSON.stringify(userResponse.data));
      }

      return redirect("/")
    } 
  } catch(e) {
    alert("아이디 또는 패스워드가 일치하지 않습니다.")

    console.log(e)
    return null;
  }
}

const Login = () => {
  return ( 
    <div className="w-full h-screen flex flex-col justify-center items-center">
      <div className="w-96 h-[430px] border rounded-2xl shadow-md flex flex-col px-6 bg-gradient-to-b from-white to-gray-50">

        <div className="p-4 flex justify-center border-b shadow-sm mb-8">
          <span className="text-2xl/9">Login</span>
        </div>

        <Form method="post">
          <div className="mb-6">
            <label htmlFor="email" className="block text-sm/6 font-medium text-gray-900">
              Email address
            </label>
            <div className="mt-2">
              <input
                id="email"
                name="email"
                type="email"
                required
                className="px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-blue-600 sm:text-sm/6"
              />
            </div>
          </div>

          <div className="mb-4">
            <label htmlFor="email" className="block text-sm/6 font-medium text-gray-900">
              Password
            </label>
            <div className="mt-2">
              <input
                id="password"
                name="password"
                type="password"
                required
                className="px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-blue-600 sm:text-sm/6"
              />
            </div>
          </div>
          <div className="mb-6">
            <div className="text-sm">
              <a href="#" className="font-semibold text-blue-500 hover:text-blue-400">
                Forgot password?
              </a>
            </div>
          </div>

          <div>
            <button
              type="submit"
              className="flex w-full justify-center rounded-md bg-black px-3 py-1.5 text-sm/6 font-semibold text-white shadow-sm hover:bg-gray-800 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              Log in
            </button>
          </div>
        </Form>

        <p className="mt-6 text-center text-sm/6 text-gray-500">
            Don't have an account?{' '}
            <Link
              to={"/join"}
              className="font-semibold text-blue-500 hover:text-blue-400"
            >
              Sign up
            </Link>
        </p>
      </div>

      <Link
        to={"/"}
      >
        <div className="mt-8 border p-2 rounded-2xl hover:bg-gray-50 cursor-pointer">
          <img src="src/assets/home.svg" width={40} />
        </div>
      </Link>

    </div>
   );
}
 
export default Login;