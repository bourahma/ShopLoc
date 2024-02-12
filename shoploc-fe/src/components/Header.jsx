import React, { useEffect, useState } from "react";
import { Button, Navbar } from "flowbite-react";
import { Link, useNavigate } from "react-router-dom";
import cart from "../images/carts.png";
import { useCart } from "./CartContext"; // Import the useCart hook

const Header = () => {
  const [loggedUser, setLoggedUser] = useState(null);
  const [userRole, setUserRole] = useState(null);
  const { itemCount } = useCart(); // Use itemCount from the CartContext

  useEffect(() => {
    const loggedUser = window.localStorage.getItem("loggedUser");
    if (loggedUser) {
      setLoggedUser(JSON.parse(loggedUser));
      setUserRole(JSON.parse(window.localStorage.getItem("userRole")));
    }
  }, []);

  const navigate = useNavigate();

  const logout = (e) => {
    e.preventDefault();
    window.localStorage.removeItem("userToken");
    window.localStorage.removeItem("loggedUser");
    window.location.reload();
    navigate("/");
  };

  return (
    <Navbar className="bg-shopgray py-4">
      <Navbar.Brand as={Link} to="/home" className="md:pl-12">
        <div className="flex gap-2">
          <svg
            width="45"
            height="45"
            viewBox="0 0 50 50"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M25 0L0 20.1075V24.5691L25 4.45721L50 24.5691V20.1075L25 0ZM25 6.96439L5.19481 22.8933V50H15.368C15.3788 46.4852 15.4545 41.3489 16.4719 36.8329C17.0346 34.3628 17.868 32.0558 19.2208 30.3038C20.5844 28.541 22.5433 27.3657 25 27.3657C27.4567 27.3657 29.4156 28.541 30.7792 30.3038C32.132 32.0558 32.9654 34.3628 33.5281 36.8329C34.5455 41.3489 34.6212 46.4852 34.632 50H44.8052V22.8933L25 6.96439ZM15.0325 18.5732C15.464 18.5842 15.9142 18.6665 16.342 18.8126C16.8191 18.9732 17.2512 19.2071 17.5897 19.4879C17.9282 19.7686 18.1595 20.085 18.2576 20.4013H31.7424C31.8405 20.085 32.0718 19.7686 32.4103 19.4879C32.7488 19.2071 33.1809 18.9732 33.658 18.8126C34.0858 18.6665 34.536 18.5842 34.9675 18.5732C35.4307 18.5645 35.8542 18.6388 36.1933 18.7882C36.5324 18.9376 36.7745 19.1566 36.8939 19.422C37.0493 19.7624 36.9939 20.1611 36.7373 20.5501C36.4808 20.939 36.0388 21.2942 35.487 21.5548C35.9648 21.8222 36.3348 22.1606 36.5431 22.5207C36.7513 22.8808 36.7871 23.2439 36.645 23.5571C36.5582 23.7512 36.4055 23.9213 36.1957 24.0576C35.9858 24.194 35.7231 24.2938 35.4224 24.3514C35.1217 24.4091 34.7891 24.4234 34.4436 24.3935C34.0981 24.3636 33.7465 24.2901 33.4091 24.1773C32.8734 23.9963 32.3962 23.7236 32.0448 23.3976C31.6933 23.0715 31.4854 22.7086 31.4502 22.3601H18.5498C18.5146 22.7086 18.3067 23.0715 17.9552 23.3976C17.6038 23.7236 17.1266 23.9963 16.5909 24.1773C16.2535 24.2901 15.9019 24.3636 15.5564 24.3935C15.2109 24.4234 14.8783 24.4091 14.5776 24.3514C14.2769 24.2938 14.0142 24.194 13.8043 24.0576C13.5945 23.9213 13.4418 23.7512 13.355 23.5571C13.2129 23.2439 13.2487 22.8808 13.4569 22.5207C13.6652 22.1606 14.0352 21.8222 14.513 21.5548C13.9612 21.2942 13.5192 20.939 13.2627 20.5501C13.0061 20.1611 12.9507 19.7624 13.1061 19.422C13.2255 19.1566 13.4676 18.9376 13.8067 18.7882C14.1458 18.6388 14.5693 18.5645 15.0325 18.5732Z"
              fill="#B24439"
            />
          </svg>
          <div>
            <h3 className="font-bold text-center">SHOPLOC</h3>
            <span className="text-shopred font-bold">
              Trouver mon commerçant
            </span>
          </div>
        </div>
      </Navbar.Brand>
      <Navbar.Toggle />
      <Navbar.Collapse className="pr-12 items-end">
        <svg
          className="hidden md:block"
          width="45"
          height="45"
          viewBox="0 0 50 50"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M25 0C28.3152 0 31.4946 1.31696 33.8388 3.66116C36.183 6.00537 37.5 9.18479 37.5 12.5C37.5 15.8152 36.183 18.9946 33.8388 21.3388C31.4946 23.683 28.3152 25 25 25C21.6848 25 18.5054 23.683 16.1612 21.3388C13.817 18.9946 12.5 15.8152 12.5 12.5C12.5 9.18479 13.817 6.00537 16.1612 3.66116C18.5054 1.31696 21.6848 0 25 0ZM25 31.25C38.8125 31.25 50 36.8438 50 43.75V50H0V43.75C0 36.8438 11.1875 31.25 25 31.25Z"
            fill="#B24439"
          />
        </svg>
        {loggedUser ? (
          <div className="flex items-center gap-3">
            <Navbar.Brand className="font-bold text-xl">
              Bonjour {loggedUser}
            </Navbar.Brand>
            {userRole === "CUSTOMER" && (
              <Link to="/cart">
                <div className="relative">
                  <img src={cart} alt="cart" className="w-6 h-6" />
                  {itemCount > 0 && (
                    <span className="absolute top-3 right-3 bg-red-500 text-white rounded-full w-4 h-4 flex items-center justify-center text-xs">
                      {itemCount}
                    </span>
                  )}
                </div>
              </Link>
            )}
            <Button className="bg-shopred" onClick={logout}>
              Se déconnecter
            </Button>
          </div>
        ) : (
          <div>
            <h2 className="font-bold text-right hidden md:block">MON COMPTE</h2>
            <div className="md:flex gap-3 justify-center text-shopred">
              <Navbar.Link as={Link} to="/login" className="text-shopred">
                Se connecter
              </Navbar.Link>
              <span className="hidden md:block">|</span>
              <Navbar.Link as={Link} to="/signup" className="text-shopred">
                S'inscrire
              </Navbar.Link>
            </div>
          </div>
        )}
      </Navbar.Collapse>
    </Navbar>
  );
};
export default Header;
