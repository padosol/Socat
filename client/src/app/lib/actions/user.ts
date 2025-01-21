'use server';

import { instance } from '../axios/axiosInstance';
import { z } from 'zod';
import { revalidatePath } from 'next/cache';
import { redirect } from 'next/navigation';

const formSchema = z.object({
  email: z.string().email({message: "이메일을 입력해주세요."}),
  password: z.string().min(4).max(8),
  username: z.string().min(2),
})

const loginForm = formSchema.omit({ username: true })
export type LoginState = {
  errors?: {
    email?: string[];
    password?: string[];
  };
  message?: string | null;
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
    };
  }

  const { email, password } = validatedFields.data;

  try {
    const response = await instance.post("/user-service/authenticate", { email, password });

    // 로그인 후 token 저장 => 유저 정보 가져와서 zustand 에 넣기
    console.log(response)
  } catch (error) {
    console.error(error);
    return {
      message: "아이디 또는 패스워드가 일치하지 않습니다."
    }
  }

  redirect('/');
}


const joinForm = formSchema.omit({});
export type JoinState = {
  errors?: {
    email?: string[];
    password?: string[];
    username?: string[];
  };
  message?: string | null;
}

export async function join(prevState: JoinState, formData: FormData) {

  const validatedFields = joinForm.safeParse({
    email: formData.get("email"),
    username: formData.get("username"),
    password: formData.get("password"),
  })

  if (!validatedFields.success) {
    return {
      errors: validatedFields.error.flatten().fieldErrors,
      message: 'Missing Fields. Failed to Create Invoice.',
    };
  }

  const {email, username, password} = validatedFields.data;


  try {
    const response = await instance.post("/user-service/users", {email, username, password})

    console.log(response)
  } catch (error) {
    console.error(error)
  }

  redirect('/login');
}