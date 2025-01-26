'use server';

import { instance } from '@/lib/axios/axiosInstance';
import { redirect } from 'next/navigation';
import { loginFormSchema } from '@/lib/schemas/auth';

const joinForm = loginFormSchema.omit({});
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