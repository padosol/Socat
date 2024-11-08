import { 
  Form,
  Link
} from "react-router-dom";

const Sidebar = () => {
  return (
    <div className="border h-full w-96 flex flex-col justify-between">
      <div className="order-1 p-4 border-t flex justify-between items-center">
        <h1 className="flex">
          <span>
            <img src="src/assets/rocket.svg">
            </img>
          </span>
          <span className="ml-4">
            Socat
          </span>
        </h1>
        <div className="">
          <Link to={`/login`}>
            <button className="border p-1 rounded-xl px-2 text-indi-500">
              Login
            </button>
          </Link>
        </div>
      </div>

      {/* 검색 폼 */}
      <div className="border-b p-4 flex">
        <Form>  
          <input 
            className="border rounded-xl p-[6px] shadow-sm" 
            type="search"
            name="roomName"
            placeholder="enter Room Name"
          >
          </input>
        </Form>
        <Form>
          <button className="ml-2 px-[10px] py-[6px] border rounded-xl text-cyan-600 shadow-sm hover:bg-slate-200">
            New
          </button>
        </Form>
      </div>

    </div>
  )
}

export default Sidebar