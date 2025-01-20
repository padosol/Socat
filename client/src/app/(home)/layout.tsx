import Image from "next/image";
import MenuNav from "../ui/menunav";
import Sidenav from "../ui/sidenav";

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="h-screen">
      <div className="border h-20 flex justify-center items-center">
        <Image src="/rocket.svg" width={32} height={32} alt="logo" />
        <span className="text-2xl font-bold">
          Socat 
        </span>
      </div>
      <div className="flex h-full px-56">
        <Sidenav />
        <div className="flex flex-col flex-1 border">
          <MenuNav />
          <div className="flex-1 p-4">
            {children}
          </div>
        </div>
      </div>
    </div>
  );
}