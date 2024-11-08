import Sidebar from "../../components/layout/Sidebar"
import MainContent from "../../components/layout/MainContent"

const MainPage = () => {
  return (
    <div className="flex h-screen w-full border"> 
      <Sidebar />
      <MainContent />
    </div>
  )
}

export default MainPage