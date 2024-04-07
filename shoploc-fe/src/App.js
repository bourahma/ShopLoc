import React from "react";
import "matchmedia-polyfill";
import "matchmedia-polyfill/matchMedia.addListener";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Products from "./pages/Products";
import SignupForm from "./components/SignupForm";
import LoginForm from "./components/LoginForm";
import { PrivateRoute } from "./components/PrivateRoute";
import { PublicRoute } from "./components/PublicRoute";
import HomeComponent from "./pages/HomeComponent";
import MerchantHome from "./pages/MerchantHome";
import Template from "./components/Template";
import FirstScreen from "./pages/FirstScreen";
import Cart from "./pages/Cart";
import AdminHome from "./pages/AdminHome";
import Profile from "./pages/Profile";
import Checkout from "./pages/Checkout";
import AddProduct from "./components/AddProduct";
import AddPromotion from "./components/AddPromotion";
import AddCategory from "./components/AddCategory";
import MerchantProducts from "./components/merchantProducts";
import ProductDetails from "./components/ProductDetails";
import UpdateProduct from "./components/UpdateProduct";
import CommerceRegistrationForm from "./components/RegisterCommerce";
import MerchantRegistrationForm from "./components/RegisterMerchant";
import LaunchPromo from "./components/LaunchPromo";
import PromoDetails from "./components/PromoDetails";
import Vfp from "./pages/Vfp"

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Template />}>
                  <Route
                      index
                      element={
                          <PublicRoute>
                              <Header />
                              <FirstScreen />
                          </PublicRoute>
                      }
                  />
                  <Route
                      path="/home"
                      element={
                          <PrivateRoute>
                              <Header />
                              <HomeComponent />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/signup"
                      element={
                          <PublicRoute>
                              <Header />
                              <SignupForm />
                          </PublicRoute>
                      }
                  />
                  <Route
                      path="/login"
                      element={
                          <PublicRoute>
                              <Header />
                              <LoginForm />
                          </PublicRoute>
                      }
                  />
                  <Route
                      path="/commercant/:commercantId"
                      element={
                          <PrivateRoute>
                              <Header />
                              <Products />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/cart"
                      element={
                          <PrivateRoute>
                              <Header />
                              <Cart />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/admin/home"
                      element={
                          <PrivateRoute>
                              <Header />
                              <AdminHome />
                          </PrivateRoute>
                      }
                  >
                      <Route index element={<CommerceRegistrationForm />} />
                      <Route
                          path="create-merchant"
                          element={<MerchantRegistrationForm />}
                      />
                      <Route path="launch-promo" element={<LaunchPromo />} />
                      <Route
                          path="promo-details/:promotionId"
                          element={<PromoDetails />}
                      />
                  </Route>
                  <Route
                      path="/profile"
                      element={
                          <PrivateRoute>
                              <Header />
                              <Profile />
                          </PrivateRoute>
                      }
                  />

                  <Route
                      path="/profile/vfp"
                      element={
                          <PrivateRoute>
                              <Header />
                              <Vfp/>
                          </PrivateRoute>
                      }
                  />

                  <Route
                      path="/checkout"
                      element={
                          <PrivateRoute>
                              <Header />
                              <Checkout />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/merchant/home"
                      element={
                          <PrivateRoute>
                              <Header />
                              <MerchantHome />
                          </PrivateRoute>
                      }
                  >
                      <Route index element={<MerchantProducts />} />
                      <Route
                          path="ajouterProduit/:commerceId"
                          element={<AddProduct />}
                      />
                      <Route
                          path="promotion/:commerceId"
                          element={<AddPromotion />}
                      />
                      <Route
                          path="addCategory/:commerceId"
                          element={<AddCategory />}
                      />
                      <Route
                          path="productDetails/:productId"
                          element={<ProductDetails />}
                      />
                      <Route
                          path="updateProduct/:productId"
                          element={<UpdateProduct />}
                      />
                  </Route>
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
