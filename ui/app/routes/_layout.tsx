import SideBar from "~/components/SideBar";
import Header from "~/components/Header";
import { Outlet } from "@remix-run/react";

export default function Layout() {
  return (
    <>
      <SideBar />
      <div className="flex flex-col flex-1">
        <Header />
        <div className="p-4">
          <Outlet />
        </div>
      </div> 
    </>
  );
}
