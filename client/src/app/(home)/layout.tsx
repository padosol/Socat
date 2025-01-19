import MenuNav from "../ui/menunav";
import Sidenav from "../ui/sidenav";

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <div className="flex h-screen">
        <Sidenav />
        <div className="flex flex-col flex-1">
          <MenuNav />
          <div className="flex-1 p-4">
            {children}
          </div>
        </div>
      </div>
    </>
  );
}