import Sidebar from "../../components/layout/Sidebar"
import MainContent from "../../components/layout/MainContent"

import { getRooms } from "../../api/room";

import { 
  useLoaderData
} from "react-router-dom";

export async function loader() {
  const response = await getRooms();

  const rooms = response.data
  return {rooms};
}

const MainPage = () => {

  const {rooms} = useLoaderData();

  return (
    <div className="flex h-screen w-full border"> 
      <Sidebar />
      <MainContent />
    </div>
  )
}

export default MainPage