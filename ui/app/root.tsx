import type { 
  LinksFunction,
} from "@remix-run/node";

import {
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
} from "@remix-run/react";

import stylesheet from "./index.css?url";

export const links: LinksFunction = () => [
  { rel: "stylesheet", href: stylesheet },
];

import SideBar from './components/SideBar';
import Header from './components/Header';
import ContentSection from "./components/ContentSection";

export default function App() {

  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body>
        <div className="flex h-screen">
          <Outlet />
          {/* <SideBar />
          <div className="flex flex-col flex-1">
            <Header />
            <div className="p-4">
              <ContentSection />
            </div>
          </div> */}
        </div>

        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}
