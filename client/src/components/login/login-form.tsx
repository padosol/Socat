"use client";

import { login, LoginState } from '@/lib/api/auth/login';
import { useActionState, useEffect } from 'react';
import Image from "next/image";
import Link from "next/link";
import { 
  type UserState,  
} from '@/stores/userInfo-store';
import {
  useUserStore
} from '@/stores/userInfo-store-provider'
import { redirect } from 'next/navigation';

export default function Form() {
  const initialState: LoginState = { message: null, errors: {}, success: false, userInfo: null };
  const [state, formAction] = useActionState(login, initialState);

  const {updateUserInfo} = useUserStore(
    (state) => state,
  )

  useEffect(() => {
    if (state.success) {
      alert("로그인 성공!");

      if (state.userInfo) {
        const userInfo: UserState = {
          username: state.userInfo.username,
          id: state.userInfo.id,
          email: state.userInfo.email,
          active: true
        }

        updateUserInfo(userInfo)

        console.log(userInfo)
      }

      state.success = false;

      redirect("/")
    }
  }, [state, state.success]);

  return (
    <div className="w-full h-screen flex flex-col justify-center items-center">
      <div className="w-96 h-[430px] border rounded-2xl shadow-md flex flex-col px-6 bg-gradient-to-b from-white to-gray-50">

        <div className="p-4 flex justify-center border-b shadow-sm mb-8">
          <span className="text-2xl/9">Login</span>
        </div>

        <form action={formAction}>
          <div className="mb-6">
            <label htmlFor="email" className="block text-sm/6 font-medium text-gray-900">
              Email address
            </label>
            <div className="mt-2">
              <input
                id="email"
                name="email"
                type="email"
                className="px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-blue-600 sm:text-sm/6"
              />
            </div>
            <div id="email-error" aria-live="polite" aria-atomic="true">
              {state.errors?.email &&
                state.errors.email.map((error: string) => (
                  <p className="text-sm text-red-500" key={error}>
                    {error}
                  </p>
                ))}
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
                className="px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-blue-600 sm:text-sm/6"
              />
            </div>
            <div id="password-error" aria-live="polite" aria-atomic="true">
              {state.errors?.password &&
                state.errors.password.map((error: string) => (
                  <p className="text-sm text-red-500" key={error}>
                    {error}
                  </p>
                ))}
            </div>
          </div>
          <div className="mb-6">
            <div className="text-sm">
              <span className="font-semibold text-blue-500 hover:text-blue-400">
                Forgot password?
              </span>
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
        </form>

        <p className="mt-6 text-center text-sm/6 text-gray-500">
            {"Don't have an account?"}
            <Link
              href={"/join"}
              className="font-semibold text-blue-500 hover:text-blue-400 ml-2"
            >
              Sign up
            </Link>
        </p>
      </div>

      <Link
        href={"/"}
      >
        <div className="mt-8 border p-2 rounded-2xl hover:bg-gray-50 cursor-pointer">
          <Image src="/home.svg" width={40} height={40} alt="Home"/>
        </div>
      </Link>

    </div>
  )
}