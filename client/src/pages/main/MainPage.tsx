import Sidebar from "../../components/layout/Sidebar"
import MainContent from "../../components/layout/MainContent"
import { getRooms } from "../../api/room";
import useUserStore from "../../store/userInfo";
import { useEffect } from "react";

export async function loader() {
  const response = await getRooms();

  const rooms = response.data
  return {rooms};
}

const MainPage = () => {

  const {userInfo, setUserInfo} = useUserStore()

  useEffect(() => {
    console.log(userInfo.isLoggedIn)
  })

  return (
    <div className="flex h-screen w-full border"> 
      <Sidebar />
      <MainContent />
    </div>
  )
}

export default MainPage