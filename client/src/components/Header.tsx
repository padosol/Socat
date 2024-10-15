const Header = ({toggleSidebar} : {toggleSidebar: () => void}) => {

  return (
    <header className=" text-white p-4 border">
      <div className="flex items-center justify-between">
        <div className="flex items-center space-x-4">
          <div className="h-6 w-6">
            <button onClick={toggleSidebar} className="focus:outline-none">
              {/* <img src="src/assets/image/menu.svg"></img> */}
            </button>
          </div>
          <h1 className="text-xl font-bold text-black cursor-pointer">SOCAT</h1>
        </div>
        <div className="flex items-center justify-evenly">
          <a className="border text-black rounded-lg p-1" href="http://localhost:3010/login">로그인</a>
        </div>
      </div>
    </header>
  )
};

export default Header;
