import { 
  Form,
  Link,
  redirect,
  Params,
} from "react-router-dom";

import { join } from "../../api/login";

interface userForm {
  username: string,
  email: string,
  password: string
}

export async function action(
  { request, params }: {request: Request, params: Params<string>}
) {
    const formData = await request.formData();

    try {
      const response = await join(formData);

      if (response.status === 201) {
        alert("회원가입 성공!")
        return redirect('/login')
      }

    } catch(e) {
      console.log(e)
      return null;
    }

}

const Join = () => {
  return (
    <div className="w-full h-screen flex flex-col justify-center items-center">
      <div className="w-96 h-[500px] border rounded-2xl shadow-md flex flex-col px-6 bg-gradient-to-b from-white to-gray-50">

        <div className="p-4 flex justify-center border-b shadow-sm mb-8">
          <span className="text-2xl/9">Create an account</span>
        </div>

        <Form method="post">
          <div className="mb-6">
            <label htmlFor="email" className="block text-sm/6 font-medium text-gray-900">
              Username
            </label>
            <div className="mt-2">
              <input
                id="username"
                name="username"
                type="text"
                required
                className="px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-blue-600 sm:text-sm/6"
              />
            </div>
          </div>

          <div className="mb-4">
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

          <div className="mb-10">
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

          <div>
            <button
              type="submit"
              className="flex w-full justify-center rounded-md bg-black px-3 py-1.5 text-sm/6 font-semibold text-white shadow-sm hover:bg-gray-800 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              Create Account
            </button>
          </div>
        </Form>

        <p className="mt-6 text-center text-sm/6 text-gray-500">
            <Link
              to={"/login"}
              className="font-semibold text-blue-500 hover:text-blue-400"
            >
              Already have an account?
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
  )
}

export default Join