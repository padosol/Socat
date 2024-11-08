import {
  createBrowserRouter,
} from "react-router-dom";


import MainPage from "./main/MainPage";
import Login from "./login/Login";

import ErrorPage from "./error/ErrorPage";

// path: "/",
// element: <Root />,
// errorElement: <ErrorPage />,
// loader: rootLoader,
// action: rootAction,
// children: [

const router = createBrowserRouter([
  {
    path: "/",
    element: <MainPage />,
    errorElement: <ErrorPage />,
    children: [
      
    ]
  },
  {
    path: '/login',
    element: <Login />
  }
])

export default router;