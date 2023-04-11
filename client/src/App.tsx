import {Header} from "./layout/Header";
import {AppRouter} from "./components/AppRouter/AppRouter";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'

function App() {
  return (
      <div className="App">
        <Header/>
        <ToastContainer/>
        <AppRouter/>
      </div>
  )
}

export default App
