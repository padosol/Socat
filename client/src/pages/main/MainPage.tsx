import Sidebar from "../../components/layout/Sidebar"
import MainContent from "../../components/layout/MainContent"

import { getRooms } from "../../api/room";

import { useEffect } from "react";

export async function loader() {
  const response = await getRooms();

  const rooms = response.data
  return {rooms};
}

const MainPage = () => {
  useEffect(() => {
    console.log("MainPage Mounted")

    return () => {
      console.log("MainPage UnMounted")
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