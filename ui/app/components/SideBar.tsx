const SideBar = () => {
  return (
    <aside className="w-20 border text-white p-4">
      <div className="flex justify-center mb-4">
        <img className="border rounded-full p-2" src="/app/assets/rocket.svg" alt="Rocket" />
      </div>
      <ul>
        <li className="mb-2 hover:bg-green-800 p-2 rounded">Menu</li>
      </ul>
    </aside>
  );
};

export default SideBar;