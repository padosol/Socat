"use client"

import Link from "next/link";
import { Button } from "@/components/button";
import { menus } from "@/lib/static-data";
import { useActionState } from "react";

import { 
  RoomState,
  createRoom
 } from "@/lib/api/rooms/create-room";


export default function Form() {
  const initialState: RoomState = { message: null, errors: {}, success: false };
  const [state, formAction] = useActionState(createRoom, initialState);

  return (
    <form action={formAction}>
      <div className="rounded-md bg-gray-50 p-4 md:p-6">

        {/* Scoat Name */}
        <div className="mb-4">
          <label htmlFor="roomName" className="mb-2 block text-sm font-medium">
            커뮤니티명
          </label>
          <div className="relative mt-2 rounded-md">
            <div className="relative">
              <input
                id="roomName"
                name="roomName"
                type="text"
                step="0.01"
                placeholder="소켓명 입력"
                className="peer block w-full rounded-md border border-gray-200 py-2 pl-4 text-sm outline-2 placeholder:text-gray-500"
                aria-describedby="amount-error"
              />
            </div>
          </div>
          {/* <div id="amount-error" aria-live="polite" aria-atomic="true">
            {state.errors?.amount &&
              state.errors.amount.map((error: string) => (
                <p className="mt-2 text-sm text-red-500" key={error}>
                  {error}
                </p>
              ))}
          </div> */}
        </div>

        {/* 소켓 설명 */ }
        <div className="mb-4">
          <label htmlFor="roomDesc" className="mb-2 block text-sm font-medium">
            커뮤니티 설명
          </label>
          <div className="relative mt-2 rounded-md">
            <div className="relative">
              <input
                id="roomDesc"
                name="roomDesc"
                type="text"
                step="0.01"
                placeholder="소켓 설명 입력"
                className="peer block w-full rounded-md border border-gray-200 py-2 pl-4 text-sm outline-2 placeholder:text-gray-500"
                aria-describedby="amount-error"
              />
            </div>
          </div>
          {/* <div id="amount-error" aria-live="polite" aria-atomic="true">
            {state.errors?.amount &&
              state.errors.amount.map((error: string) => (
                <p className="mt-2 text-sm text-red-500" key={error}>
                  {error}
                </p>
              ))}
          </div> */}
        </div>
        
        <div className="mb-4">
          <label htmlFor="roomType" className="mb-2 block text-sm font-medium">
            카테고리 선택
          </label>
          <div className="relative">
            <select
              id="roomType"
              name="roomType"
              className="peer block w-full cursor-pointer rounded-md border border-gray-200 py-2 pl-4 text-sm outline-2 placeholder:text-gray-500"
              defaultValue=""
              aria-describedby="customer-error"
            >
              <option value="" disabled>
                카테고리 선택
              </option>
              {menus.map((menu) => (
                <option key={menu.id} value={menu.id} className="">
                  {menu.name}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>

      <div className="mt-6 flex justify-end gap-4">
        <Link
          href="/socat"
          className="flex h-10 items-center rounded-lg bg-gray-100 px-4 text-sm font-medium text-gray-600 transition-colors hover:bg-gray-200"
        >
          취소
        </Link>
        <Button type="submit">소켓 생성</Button>
      </div>
    </form>
  )
}