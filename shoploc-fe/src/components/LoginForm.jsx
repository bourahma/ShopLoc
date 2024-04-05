import React, { useState } from "react";
import { Alert, Button, Label, TextInput } from "flowbite-react";
import { Link, useNavigate } from "react-router-dom";
import loginService from "../services/login";
import * as Yup from "yup";
import { ErrorMessage, Formik } from "formik";

const LoginForm = () => {
  const [error, setError] = useState(null);
  const [userRole, setUserRole] = useState(null);

  const initialUser = {
    username: "",
    password: "",
  };

  const navigate = useNavigate();

  const userLogin = (values, loginFn, successUrl) => {
    loginFn(values)
      .then((data) => {
        window.localStorage.setItem(
          "loggedUser",
          JSON.stringify(values.username)
        );
        window.localStorage.setItem(
          "userToken",
          JSON.stringify(data["access-token"])
        );
        window.localStorage.setItem(
          "userRole",
          JSON.stringify(userRole || data["role"])
        );
        navigate(successUrl);
      })
      .catch((error) => {
        console.log(error);
        if (error.response?.data?.message) {
          setError(error.response.data);
        } else {
          setError(error);
        }
      });
  };

  const userLoginWithRole = (values, roleUser) => {
    if (roleUser === "ADMINISTRATOR") {
      userLogin(
        values,
        (values) => loginService.adminLogin(values),
        "/admin/home"
      );
    } else if (roleUser === "MERCHANT") {
      userLogin(
        values,
        (values) => loginService.merchantLogin(values),
        "/merchant/home"
      );
    } else {
      userLogin(
        values,
        (values) => loginService.customerLogin(values),
        "/home"
      );
    }
  };

  if (error) {
    setTimeout(() => {
      setError(null);
    }, 5000);
  }

  return (
    <>
      {error && <Alert color="failure">{error.message}</Alert>}

      <div className="flex flex-wrap justify-center gap-20 my-12 mx-6">
        <Formik
          initialValues={initialUser}
          validationSchema={Yup.object({
            username: Yup.string()
              .max(15, "Doit être 15 caractères ou moins")
              .required("Champ requis"),
            password: Yup.string()
              .min(8, "Doit être 8 caractères ou plus")
              .max(20, "Doit être 20 caractères ou moins")
              .required("Champ requis"),
          })}
          onSubmit={(values, { setSubmitting }) => {
            userLoginWithRole(values, userRole);
            setSubmitting(false);
          }}
        >
          {({
            values,
            errors,
            touched,
            handleChange,
            handleSubmit,
            isSubmitting,
          }) => (
            <form
              className="flex max-w-md flex-col gap-4"
              onSubmit={handleSubmit}
            >
              {!userRole && (
                <h1 className="text-lg font-bold">Connexion client</h1>
              )}
              {userRole === "ADMINISTRATOR" && (
                <h1 className="text-lg font-bold">Connexion administrateur</h1>
              )}
              {userRole === "MERCHANT" && (
                <h1 className="text-lg font-bold">Connexion commerçant</h1>
              )}
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="username">Nom d'utilisateur</Label>
                </div>
                <TextInput
                  id="username"
                  type="text"
                  placeholder="Votre nom d'utilisateur"
                  value={values.username}
                  error={errors.username}
                  fieldtouched={touched.username?.toString()}
                  onChange={handleChange}
                />
                <ErrorMessage
                  name="username"
                  component="div"
                  className="text-red-500 text-xs"
                />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="password">Mot de passe</Label>
                </div>
                <TextInput
                  id="password"
                  type="password"
                  placeholder="Votre mot de passe"
                  onChange={handleChange}
                  value={values.password}
                  error={errors.password}
                  fieldtouched={touched.password?.toString()}
                />
                <ErrorMessage
                  name="password"
                  component="div"
                  className="text-red-500 text-xs"
                />
              </div>
              <Button
                type="submit"
                className="bg-shopred"
                disabled={isSubmitting}
              >
                Connexion
              </Button>
            </form>
          )}
        </Formik>
        <div className="flex max-w-md flex-col gap-4">
          {!userRole && (
            <div>
              Vous n'avez pas de encore compte ? &nbsp;
              <Link to="/signup" className="text-shopred font-bold">
                Inscrivez-vous
              </Link>
            </div>
          )}
          {(!userRole || userRole === "MERCHANT") && (
            <Button
              className="border-l-blue-800 hover:bg-shopred"
              onClick={() => setUserRole("ADMINISTRATOR")}
            >
              <span className="flex">Êtes-vous administrateur ?</span>
            </Button>
          )}
          {(!userRole || userRole === "ADMINISTRATOR") && (
            <Button
              className="border-l-blue-800 hover:bg-shopred"
              onClick={() => setUserRole("MERCHANT")}
            >
              Êtes-vous commerçant ?
            </Button>
          )}
          {userRole && (
            <Button className="" onClick={() => setUserRole("CUSTOMER")}>
              Êtes-vous client ?
            </Button>
          )}
        </div>
      </div>
    </>
  );
};

export default LoginForm;
