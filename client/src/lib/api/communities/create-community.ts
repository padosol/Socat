"use server"

import { instance } from "@/lib/axios/axiosInstance";
import { communitySchema } from "@/lib/schemas/rooms";
import { revalidatePath } from 'next/cache';
import { redirect } from 'next/navigation';

export type CommunityState = {
  errors?: {
    communityName?: string[];
    communityDesc?: string[];
    topicId?: string[];
  };
  message?: string | null;
  success: boolean;
};

const communityForm = communitySchema.omit({communityId: true, })

export async function createCommunity(prevState: CommunityState, formData: FormData) { 

  const validatedFields = communityForm.safeParse({
    communityName: formData.get("communityName"),
    communityDesc: formData.get("communityDesc"),
    topicId: formData.get("topicId"),
  })
  
  if (!validatedFields.success) {
    return {
      errors: validatedFields.error.flatten().fieldErrors,
      message: 'Missing Fields. Failed to Create Invoice.',
      success: false,
    };
  }

  const form = validatedFields.data;
  
  try {
    const resposne = await instance.post("/community-service/communities", form);

    if (!resposne.data.success) {
      return {
        errors: {},
        message: '서버에러입니다.',
        success: false,
      }
    }

  } catch (error) {
    console.error(error)
    return {
      errors: {},
      message: 'Missing Fields. Failed to Create Invoice.',
      success: false,
    };
  }

  revalidatePath('/socat');
  redirect('/socat');
}