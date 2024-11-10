import {
  Outlet
} from "react-router-dom"


const Content = () => {
  return (
    <div className="border p-8 h-full">
      <Outlet />
    </div>
  )
}

export default Content