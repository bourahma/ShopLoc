import React from "react";
import {
    BrowserRouter,
    Outlet,
    Route,
    Routes,
    Navigate,
} from "react-router-dom";
import HomeComponent from "./components/HomeComponent";
import Header from "./components/Header";
import Products from "./components/Products";
import LegacyInfosComponent from "./components/Footer";
import SignupForm from "./components/SignupForm";
import LoginForm from "./components/LoginForm";
import Template from "./components/Template";
import FirstScreen from "./components/FirstScreen";

function App() {
    const loggedUser = window.localStorage.getItem("userToken");

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Template />}>
                    {/* Page d'accueil */}
                    <Route
                        index
                        element={
                            <>
                                {loggedUser ? (
                                    <HomeComponent />
                                ) : (
                                    <FirstScreen />
                                )}
                            </>
                        }
                    />

                    {/* Page des produits */}
                    <Route
                        path="/commercant/:commercantId"
                        element={
                            <>
                                {loggedUser ? (
                                    <Products />
                                ) : (
                                    <Navigate to="/" />
                                )}
                            </>
                        }
                    />

                    {/* Page d'inscription */}
                    <Route
                        path="/signup"
                        element={
                            <>
                                {loggedUser ? (
                                    <Navigate to="/" />
                                ) : (
                                    <SignupForm />
                                )}
                            </>
                        }
                    />

                    {/* Page de connexion */}
                    <Route
                        path="/login"
                        element={
                            <>
                                {loggedUser ? (
                                    <Navigate to="/" />
                                ) : (
                                    <LoginForm />
                                )}
                            </>
                        }
                    />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
