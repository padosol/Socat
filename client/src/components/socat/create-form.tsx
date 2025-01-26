"use client"

import Link from "next/link";
import { Button } from "@/components/button";
import { menus } from "@/lib/static-data";
import { useUserStore } from "@/stores/userInfo-store-provider";

export default function Form() {

  const { active, username, id } = useUserStore(
    (state) => state,
  )

  return (
    <form>
      <div className="rounded-md bg-gray-50 p-4 md:p-6">
        <div>
          {id}
        </div>

        {/* Scoat Name */}
        <div className="mb-4">
          <label htmlFor="amount" className="mb-2 block text-sm font-medium">
            소켓명
          </label>
          <div className="relative mt-2 rounded-md">
            <div className="relative">
              <input
                id="name"
                name="name"
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
          <label htmlFor="amount" className="mb-2 block text-sm font-medium">
            소켓 설명
          </label>
          <div className="relative mt-2 rounded-md">
            <div className="relative">
              <input
                id="desc"
                name="desc"
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
          <label htmlFor="customer" className="mb-2 block text-sm font-medium">
            카테고리 선택
          </label>
          <div className="relative">
            <select
              id="customer"
              name="customerId"
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