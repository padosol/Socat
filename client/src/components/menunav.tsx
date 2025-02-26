'use client'

import Link from "next/link";
import { useUserStore } from "@/stores/userInfo-store-provider";
import { UserCircleIcon } from "@heroicons/react/24/outline";
import { useState } from "react";
import { logout } from "@/lib/api/auth/logout";
import { Topic } from "@/lib/definitions";
import { useSearchParams, usePathname } from 'next/navigation';
import clsx from 'clsx';
import { useRouter } from "next/navigation";

export default function MenuNav({
  topics
}: {
  topics: Topic[]
}) {
  const {replace} = useRouter();

  const pathname = usePathname();
  const searchParams = useSearchParams();
  const topic = topics.find((topic) => topic.topicId === searchParams.get("type"));
  const type = topic ? topic.topicId : "all"; 

  const { active, username } = useUserStore(
    (state) => state,
  )

  const [showLogoutBox, setShowLogoutBox] = useState(false);

  const handleUsernameClick = () => {
    setShowLogoutBox(!showLogoutBox);
  };

  const handleLogout = async () => {
    // 로그아웃 로직 추가
    await logout();

    window.location.href = "/"
  };

  return (
    <div className="h-12  flex items-center justify-between border-b border-gray-300 px-4">
      <div className="flex space-x-4">
        <Link href={'/'} className="text-gray-700 hover:text-gray-900">
          홈
        </Link>
        <Link href={{
            pathname: '/socat',
            query: { type: "all" }
          }} className={clsx(
            "text-gray-700 hover:text-gray-900",
            {
              'bg-sky-100 text-blue-600': (pathname === "/socat" && type === "all")
            }
          )}>
            전체
          </Link>
        {topics && topics.map((topic) => (
          <Link key={topic.topicId} href={{
            pathname: '/socat',
            query: { type: topic.topicId }
          }} className={clsx(
            "text-gray-700 hover:text-gray-900",
            {
              'bg-sky-100 text-blue-600': (pathname === "/socat" && type === topic.topicId)
            }
          )}>
            {topic.topicName}
          </Link>
        ))}
      </div>

      {active ? 
        <div className="rounded-md p-1 cursor-pointer">
          <div className="flex w-20 justify-center items-center" onClick={handleUsernameClick}> 
            <UserCircleIcon className="size-6 mr-1"/>
            <span className="hover:text-slate-500 ">
              {username}
            </span>
          </div>

          {showLogoutBox && (
          <div className="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-md shadow-lg">
            <button
              onClick={handleLogout}
              className="block w-full text-left px-4 py-2 text-gray-700 hover:bg-gray-100"
            >
              로그아웃
            </button>
          </div>
        )}

      </div> 
      :       
      <Link href={'/login'} className="text-gray-700 hover:text-gray-900">
        로그인
      </Link>}
    </div>
  );
}