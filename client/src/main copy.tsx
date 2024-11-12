import * as React from "react"
import { createRoot } from 'react-dom/client'
// import './index.css'
import './tutorial.css'

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";

import Root, { 
  loader as rootLoader,
  action as rootAction,
} from "./routes/root";

import ErrorPage from "./error-page";
import Contact, {
  loader as contactLoader,
  action as contactAction,
} from "./routes/contact";

import EditContact, {
  action as editAction,
} from "./routes/edit";

import { action as destroyAction } from "./routes/destroy";
import Index from "./routes/index";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    errorElement: <ErrorPage />,
    loader: rootLoader,
    action: rootAction,
    children: [
      {
        errorElement: <ErrorPage />,
        children: [
          { index: true, element: <Index /> },
          {
            path: "contacts/:contactId",
            element: <Contact />,
            loader: contactLoader,
            action: contactAction
          },
          {
            path: "contacts/:contactId/edit",
            element: <EditContact />,
            loader: contactLoader,
            action: editAction
          },
          {
            path: "contacts/:contactId/destroy",
            action: destroyAction,
            errorElement: <div>Oop! There was an error</div>,
          },
        ]
      }
    ]
  },

]);

createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>
)