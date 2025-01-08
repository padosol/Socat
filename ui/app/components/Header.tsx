import { NavLink, useNavigate } from 'react-router-dom';

const Header = () => {
  const navigate = useNavigate();

  const handleLoginClick = () => {
    navigate('/login');
  };

  return (
    <header className="bg-white text-black p-4 flex justify-between items-center shadow">
      <nav className="flex space-x-6 px-2">
        <NavLink
            to="/content/all"
            className={({ isActive }) =>
              isActive ? 'text-green-500 font-bold' : 'text-green-700 hover:text-green-900 font-bold'
            }
          >
            전체
          </NavLink>
          <NavLink
            to="/content/games"
            className={({ isActive }) =>
              isActive ? 'text-green-500 font-bold' : 'text-green-700 hover:text-green-900 font-bold'
            }
          >
            게임
          </NavLink>
          <NavLink
            to="/content/stocks"
            className={({ isActive }) =>
              isActive ? 'text-green-500 font-bold' : 'text-green-700 hover:text-green-900 font-bold'
            }
          >
            주식
          </NavLink>
          <NavLink
            to="/content/movies"
            className={({ isActive }) =>
              isActive ? 'text-green-500 font-bold' : 'text-green-700 hover:text-green-900 font-bold'
            }
          >
            영화
          </NavLink>
          <NavLink
            to="/content/music"
            className={({ isActive }) =>
              isActive ? 'text-green-500 font-bold' : 'text-green-700 hover:text-green-900 font-bold'
            }
          >
            음악
          </NavLink>
          <NavLink
            to="/content/travel"
            className={({ isActive }) =>
              isActive ? 'text-green-500 font-bold' : 'text-green-700 hover:text-green-900 font-bold'
            }
          >
            여행
          </NavLink>  
        </nav>
      <button
        className="bg-green-700 text-white px-4 py-2 rounded"
        onClick={handleLoginClick}
      >
        Login
      </button>
    </header>
  );
};

export default Header;