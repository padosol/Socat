const MainHeader = () => {

  return (
    <header className=" text-white border w-full shadow-sm">
      <div className="flex items-center justify-between h-16 px-2">
        <div className="flex items-center space-x-4">
          <div className="h-6 w-6">
            <button className="focus:outline-none">
              {/* <img src="src/assets/image/menu.svg"></img> */}
            </button>
          </div>
          <h1 className="text-xl font-bold text-black cursor-pointer">SOCAT</h1>
        </div>
        <div className="flex items-center justify-evenly">
          <a className="border text-black rounded-xl p-2 hover:bg-slate-300" href="http://localhost:3010/login">로그인</a>
        </div>
      </div>
    </header>
  )
};

export default MainHeader;
