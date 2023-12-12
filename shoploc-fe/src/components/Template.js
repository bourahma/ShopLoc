import { Outlet } from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";

function UserHomeComponent() {
    return (
        <div className="flex flex-col min-h-screen">
            <Header />
            <div className="flex-1">
                <Outlet />
            </div>
            <Footer />
        </div>
    );
}

export default UserHomeComponent;
