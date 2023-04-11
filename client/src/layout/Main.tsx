import {Outlet, useLocation, useNavigate} from "react-router-dom";
import {useEffect} from "react";

export const Main = () => {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    if (location.pathname === '/')
      navigate("/clients");
  }, [navigate]);

  return (
      <main className='container content'>
        <Outlet/>
      </main>
  )
}