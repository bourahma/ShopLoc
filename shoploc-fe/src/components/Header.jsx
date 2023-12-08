import React, { useEffect, useState } from "react";
import { Button, Navbar } from "flowbite-react";
import { Link, useNavigate } from "react-router-dom";

const Header = () => {
  const [loggedUser, setLoggedUser] = useState(null);

  useEffect(() => {
    const loggedUser = window.localStorage.getItem("loggedUser");
    if (loggedUser) {
      setLoggedUser(JSON.parse(loggedUser));
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
    <Navbar fluid rounded>
      <Navbar.Brand href="#">
        <img
          src="https://picsum.photos/200/300"
          className="mr-3 h-6 sm:h-9"
          alt="shoploc logo"
        />
        <span className="self-center whitespace-nowrap text-xl font-semibold dark:text-white">
          ShopLoc
        </span>
      </Navbar.Brand>
      <Navbar.Toggle />
      <Navbar.Collapse>
        {loggedUser ? (
          <>
            <h2>Bonjour {loggedUser}</h2>
            <Button className="bg-black" onClick={logout}>
              Se déconnecter
            </Button>
          </>
        ) : (
          <>
            <Navbar.Link as={Link} to="/login">
              Se connecter
            </Navbar.Link>
            <Navbar.Link as={Link} to="/signup">
              S'inscrire
            </Navbar.Link>
          </>
        )}
      </Navbar.Collapse>
    </Navbar>
  );
};
export default Header;
