import {
  createBrowserRouter,
} from "react-router-dom";

import MainPage, {
  loader as mainPageLoader
} from "./main/MainPage";

import Login, {
  action as loginAction
} from "./login/Login";

import Join, {
  action as joinAction
} from "./login/Join";

import Room, {
  loader as roomLoader
} from "./main/room/Room";

import RoomIndex from "./main/room/RoomIndex";
import ErrorPage from "./error/ErrorPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <MainPage />,
    errorElement: <ErrorPage />,
    loader: mainPageLoader,
    children: [
      {
        index: true, element: <RoomIndex />,
      },
      {
        path: "/room/:roomId",
        element: <Room />,
        loader: roomLoader,
        errorElement: <ErrorPage />
      }
    ]
  },
  {
    path: '/login',
    element: <Login />,
    action: loginAction
  },
  {
    path: "/join",
    element: <Join />,
    action: joinAction
  }
])

export default router;