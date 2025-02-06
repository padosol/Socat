'use server';

import { instance } from '@/lib/axios/axiosInstance';
import { cookies } from 'next/headers'
import { User } from '@/lib/definitions';
import { loginFormSchema } from '@/lib/schemas/auth';
import axios from 'axios';
import { UserState } from '@/stores/userInfo-store';
import { redirect } from 'next/navigation';

const loginForm = loginFormSchema.omit({ username: true })

export type LoginState = {
  errors?: {
    email?: string[];
    password?: string[];
  };
  message?: string | null;
  success: boolean;
  userInfo?: User | null;
};


export async function login(prevState: LoginState, formData: FormData) {
  const validatedFields = loginForm.safeParse({
    email: formData.get("email"),
    password: formData.get("password")
  })

  if (!validatedFields.success) {
    return {
      errors: validatedFields.error.flatten().fieldErrors,
      message: 'Missing Fields. Failed to Create Invoice.',
      success: false,
      userInfo: null
    };
  }

  const { email, password } = validatedFields.data;

  try {
    const response = await axios.post("http://localhost:8000/user-service/authenticate", { email, password });

    console.log(response)

    // 로그인 후 token 저장 => 유저 정보 가져와서 zustand 에 넣기
    const cookieStore = await cookies();
    cookieStore.set("accessToken", response.data.accessToken)
    cookieStore.set("refreshToken", response.data.refreshToken)

  } catch (error) {
    console.error(error);
    return {
      message: "아이디 또는 패스워드가 일치하지 않습니다.",
      success: false
    }
  }

  redirect("/")
}

export async function getUserInfoByToken(): Promise<UserState> {

  const response = await instance.get(`/user-service/users`)

  return response.data;
}