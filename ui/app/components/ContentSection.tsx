import { Outlet } from "@remix-run/react";

const ContentSection = () => {
  return (
    <div>
      <Outlet />
    </div>
  );
};

export default ContentSection;