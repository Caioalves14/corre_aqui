import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "../components/Login";
import Cadastro from "../components/Cadastro";
import Home from "../components/Home";
function AppRouter() {
    return (
        <>
            <Router>
                <Routes>
                    <Route path="/" element = {<Login />}></Route>
                    <Route path="/Cadastro.jsx" element = {<Cadastro />}></Route>
                    <Route path="/home" element = {<Home />}></Route>
                </Routes>
            </Router>
        </>
    )
}
export default AppRouter;