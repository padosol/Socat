import Sidebar from "../../components/layout/Sidebar"
import MainContent from "../../components/layout/MainContent"
import { getRooms } from "../../api/room";
import { useEffect } from "react";
import { getUserInfo } from "../../api/user";
import { useLoaderData } from "react-router-dom";

import useUserStore, {IUserInfo} from "../../store/userInfo";

export async function loader() {

  const response = await getRooms();

  const userInfoResponse = await getUserInfo();

  const rooms = response.data
  const userInfo = userInfoResponse.data;
  return {rooms, userInfo};
}

const MainPage = () => {

  const {userInfo} = useLoaderData() as {userInfo: IUserInfo};
  const {setUserInfo} = useUserStore();

  useEffect(() => {
    if (userInfo) {
      setUserInfo(userInfo)
    }
  }, [])

  return (
    <div className="flex h-screen w-full border"> 
      <Sidebar />
      <MainContent />
    </div>
  )
}

export default MainPage