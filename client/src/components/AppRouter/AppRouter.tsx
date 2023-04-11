import {Route, Routes} from "react-router-dom";
import {Main} from "../../layout/Main";
import {routes} from "./routes";

export const AppRouter = () => {

  return (
      <Routes>
        <Route element={<Main/>}>
          {
            routes.map(route => (
                <Route element={route.element} path={route.path} key={route.path}/>
            ))
          }
        </Route>
      </Routes>
  )
}