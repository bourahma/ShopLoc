
import { Outlet, Link } from "react-router-dom";

function UserHomeComponent() {
  return <main className="h-screen w-screen">
    
            <nav className="border-gray-200 dark:bg-gray-900 py-10 mx-16">
            </nav>

            <Outlet/>
        </main>;
}

  export default UserHomeComponent;