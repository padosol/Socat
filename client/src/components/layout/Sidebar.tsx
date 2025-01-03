import { useEffect, useState } from "react";
import { 
  Form,
  Link,
  useLoaderData
} from "react-router-dom";

import useUserStore from "../../store/userInfo";

const Sidebar = () => {

  const { userInfo } = useUserStore();
  const {rooms} = useLoaderData();

  useEffect(() => {

  }, [])

  return (
    <div className="border h-full w-96 flex flex-col justify-between">
      <div className="order-1 p-4 border-t flex justify-between items-center h-[70px]">
        <h1 className="flex">
          <span>
            <img src="/src/assets/rocket.svg">
            </img>
          </span>
          <span className="ml-4">
            Socat
          </span>
        </h1>
        <div className="">
          {
            userInfo.isLoggedIn 
            ? 
            <Link to={'/mypage'}>
              <div className="rounded-full border p-1 hover:border-black hover:border-2 cursor-pointer"

              >
                <img src="/src/assets/profile.svg"></img>
              </div> 
            </Link>
            :
            <Link to={`/login`}>
              <button className="border p-1 rounded-xl px-2 text-indi-500">
                Login
              </button>
            </Link>
          }
        </div>
      </div>

      {/* 검색 폼 */}
      <div className="flex flex-col">
        <div className="p-4 flex border-b">
          <Form>  
            <input 
              className="border rounded-xl p-[6px] shadow-sm" 
              type="search"
              name="roomName"
              placeholder="채팅방 검색"
            >
            </input>
          </Form>
          <Link to={'/room/create'}>
            <button className="ml-2 px-[10px] py-[6px] border rounded-xl text-cyan-600 shadow-sm hover:bg-slate-200">
              New
            </button>
          </Link>
        </div>
        <div className="mt-2">
          {rooms.length ? (
            <ul>
              {rooms.map((room) => (
                <Link to={`/room/${room.roomId}`} key={room.roomId}>
                  <li className="p-3 hover:bg-slate-300 cursor-pointer m-2 rounded-lg">{room.roomName}</li>
                </Link>
              ))}
            </ul>
          ) : (
            <p>No Rooms</p>
          )}
        </div>
      </div>

    </div>
)
}

export default Sidebar