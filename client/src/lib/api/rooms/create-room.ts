"use server"

import { instance } from "@/lib/axios/axiosInstance";
import { roomSchema } from "@/lib/schemas/rooms";
import { revalidatePath } from 'next/cache';
import { redirect } from 'next/navigation';

export type RoomState = {
  errors?: {
    roomName?: string[];
    roomDesc?: string[];
    roomType?: string[];
  };
  message?: string | null;
  success: boolean;
};

const roomForm = roomSchema.omit({roomId: true, })

export async function createRoom(prevState: RoomState, formData: FormData) { 

  const validatedFields = roomForm.safeParse({
    roomName: formData.get("roomName"),
    roomDesc: formData.get("roomDesc"),
    roomType: formData.get("roomType"),
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
    const resposne = await instance.post("/room-service/rooms", form);

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