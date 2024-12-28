import { Form, redirect, Link } from "react-router-dom";
import { ChangeEvent, useEffect, useState } from "react";
import { createRoom } from "../../../api/room"
import { IUserInfo } from "@/types/user.ts"

//test

export async function action({request, params}) {
  const formData = await request.formData();
  const data = Object.fromEntries(formData);

  const response = await createRoom(data.roomName);
  console.log(response)

  return redirect("/")
}

const RoomCreate = () => {

  const [roomName, setRoomName] = useState<string>("");
  const [userInfo, setUserInfo] = useState<IUserInfo>();

  useEffect(() => {
    const userInfo = localStorage.getItem("user-info");
    if (userInfo) {
      setUserInfo(JSON.parse(userInfo))
    } else {
      return
    }
  }, [])

  function handleRoomNameChange(e: ChangeEvent<HTMLInputElement>){
    setRoomName(e.target.value)
  }

  return ( 
    <div>
      <button className="px-1 hover:scale-150 mb-2">
        <Link to={"/"}>
          <img src="/src/assets/arrow_left.svg" />
        </Link>
      </button>
      <Form method="post">
        <div className="border rounded-lg p-4 w-[300px] h-[150px] shadow-lg">
          <div className="mb-4">
            <label>방 제목: </label>
            <input 
              name="roomName"
              required
              className="border border-black rounded-lg px-1" value={roomName} onChange={handleRoomNameChange} max={20}/>
          </div>
          <div className="mb-4">
            <label>소유자: </label>
            <span>
              {userInfo ? userInfo.userName : ''}
            </span>
          </div>
          <div>
            <button 
              type="submit"
              className="border rounded-xl px-2 py-1 bg-slate-300  hover:bg-slate-200"
            >생성</button>
          </div>
        </div>
      </Form>
    </div>
   );
}
 
export default RoomCreate;