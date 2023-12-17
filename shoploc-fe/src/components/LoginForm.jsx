import React, { useState } from "react";
import { Alert, Button, Label, TextInput } from "flowbite-react";
import { Link, useNavigate } from "react-router-dom";
import loginService from "../services/login";
import * as Yup from "yup";
import { ErrorMessage, Formik } from "formik";

const LoginForm = () => {
  const [error, setError] = useState(null);
  const initialUser = {
    username: "",
    password: "",
  };

  const navigate = useNavigate();

  const loginUser = (values) => {
    loginService
      .login(values)
      .then((data) => {
        console.log(data);
        window.localStorage.setItem(
          "loggedUser",
          JSON.stringify(values.username)
        );
        window.localStorage.setItem(
          "userToken",
          JSON.stringify(data["access-token"])
        );
        navigate("/home");
      })
      .catch((error) => {
        console.log(error);
        setError(error.response.data);
      });
  };

  if (error) {
    setTimeout(() => {
      setError(null);
    }, 5000);
  }

  return (
    <>
      {error && <Alert color="failure">{error.message}</Alert>}
      <div className="flex justify-center gap-10 my-12 mx-6">
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
            loginUser(values);
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
                className="bg-black"
                disabled={isSubmitting}
              >
                Connexion
              </Button>
            </form>
          )}
        </Formik>
        <div className="flex max-w-md flex-col gap-4">
          Vous n'avez pas de encore compte ? &nbsp;
          <Link to="/signup" className="text-blue-500">
            Inscrivez-vous
          </Link>
        </div>
      </div>
    </>
  );
};

export default LoginForm;
