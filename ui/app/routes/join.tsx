import { 
  Form,
  json,
  Link,
  useActionData,
} from "react-router-dom";


import { ActionFunctionArgs, redirect } from "@remix-run/node";

import { IJoinData, join } from "~/api/user";

export const action = async ({ request }: ActionFunctionArgs) => {

  const formData = await request.formData();
  const username = formData.get('username') as string | null;
  const email = formData.get('email') as string | null;
  const password = formData.get('password') as string | null;

  if (!username || !email || !password) {
    return json({ error: 'All fields are required' }, { status: 400 });
  }

  const joinData: IJoinData = {
    username,
    email,
    password
  }

  try {
    const joinResponse = await join(joinData);

    console.log(joinResponse)

    if (!joinResponse.data.success) {
      return json({ error: joinResponse.data.message }, { status: 400 });      
    }

    return redirect("/login");
  } catch (e) {
    return json({ status: 500 });   
  }
};


const Join = () => {
  
  const data = useActionData<typeof action>();
  console.log(data)

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
          <img src="/app/assets/home.svg" width={40} alt="home.svg"/>
        </div>
      </Link>

    </div>
  )
}

export default Join