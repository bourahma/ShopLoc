import React, { useState } from "react";
import { Alert, Button, Label, TextInput } from "flowbite-react";
import { Link, useNavigate } from "react-router-dom";
import signupService from "../services/signup";
import { ErrorMessage, Field, Formik } from "formik";
import * as Yup from "yup";

const SignupForm = () => {
  const [error, setError] = useState(null);

  const initialUser = {
    username: "",
    lastname: "",
    firstname: "",
    password: "",
    confirmedPassword: "",
    email: "",
    phoneNumber: "",
    agree: false,
  };

  const navigate = useNavigate();

  const createUser = (values) => {
    delete values.agree;
    signupService
      .signup(values)
      .then((data) => {
        console.log(data);
        navigate("/login");
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
      <div className="flex justify-center my-12 mx-6">
        <Formik
          initialValues={initialUser}
          validationSchema={Yup.object({
            username: Yup.string()
              .max(15, "Doit être 15 caractères ou moins")
              .required("Champ requis"),
            lastname: Yup.string()
              .max(20, "Doit être 20 caractères ou moins")
              .required("Champ requis"),
            firstname: Yup.string()
              .max(20, "Doit être 20 caractères ou moins")
              .required("Champ requis"),
            password: Yup.string()
              .min(8, "Doit être 8 caractères ou plus")
              .max(20, "Doit être 20 caractères ou moins")
              .required("Champ requis"),
            confirmedPassword: Yup.string()
              .oneOf(
                [Yup.ref("password"), null],
                "Les mots de passe doivent correspondre"
              )
              .required("Champ requis"),
            email: Yup.string()
              .email("Adresse e-mail invalide")
              .required("Champ requis"),
            phoneNumber: Yup.string()
              .matches(/^[0-9]+$/, "Doit être un numéro de téléphone valide")
              .min(10, "Doit être 10 caractères ou plus")
              .max(10, "Doit être 10 caractères ou moins")
              .required("Champ requis"),
            agree: Yup.boolean()
              .oneOf([true], "Vous devez accepter les termes et conditions")
              .required("Champ requis"),
          })}
          onSubmit={(values, { setSubmitting }) => {
            createUser(values);
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
              className="grid grid-cols-2 gap-4 max-w-2xl mx-auto"
              onSubmit={handleSubmit}
            >
              <div className="flex flex-col gap-4">
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
                    <Label htmlFor="lastname">Nom de famille</Label>
                  </div>
                  <TextInput
                    id="lastname"
                    type="text"
                    placeholder="Votre nom de famille"
                    value={values.lastname}
                    error={errors.lastname}
                    fieldtouched={touched.lastname?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="lastname"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="firstname">Prénom</Label>
                  </div>
                  <TextInput
                    id="firstname"
                    type="text"
                    placeholder="Votre prénom"
                    value={values.firstname}
                    error={errors.firstname}
                    fieldtouched={touched.firstname?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="firstname"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
              </div>
              <div className="flex flex-col gap-4">
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="password">Mot de passe</Label>
                  </div>
                  <TextInput
                    id="password"
                    type="password"
                    placeholder="Votre mot de passe"
                    value={values.password}
                    error={errors.password}
                    fieldtouched={touched.password?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="password"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="confirmedPassword">
                      Confirmer le mot de passe
                    </Label>
                  </div>
                  <TextInput
                    id="confirmedPassword"
                    type="password"
                    placeholder="Confirmer votre mot de passe"
                    value={values.confirmedPassword}
                    error={errors.confirmedPassword}
                    fieldtouched={touched.confirmedPassword?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="confirmedPassword"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="email">Adresse e-mail</Label>
                  </div>
                  <TextInput
                    id="email"
                    type="email"
                    placeholder="addresse@gmail.com"
                    value={values.email}
                    error={errors.email}
                    fieldtouched={touched.email?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="email"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="phoneNumber">Numéro de téléphone</Label>
                </div>
                <TextInput
                  id="phoneNumber"
                  type="text"
                  placeholder="Votre numéro de téléphone"
                  value={values.phoneNumber}
                  error={errors.phoneNumber}
                  fieldtouched={touched.phoneNumber?.toString()}
                  onChange={handleChange}
                />
                <ErrorMessage
                  name="phoneNumber"
                  component="div"
                  className="text-red-500 text-xs"
                />
              </div>
              <div className="flex flex-col ">
                <div className="flex items-center gap-2">
                  <Field type="checkbox" id="agree" name="agree" />
                  <Label htmlFor="agree" className="flex">
                    J'accepte les &nbsp;
                    <Link
                      to="https://docs.google.com/document/d/e/2PACX-1vQkr79lY7kZhUDVaGT1RWqroKso5BSf5AAWy6R2lTSoBu2KzAHVBoveYEZwygwaz-TU9RZMRCGNoEbi/pub"
                      className="text-cyan-600 hover:underline dark:text-cyan-400"
                      target="_blank"
                    >
                      termes et conditions
                    </Link>
                  </Label>
                </div>
                <ErrorMessage
                  name="agree"
                  component="div"
                  className="text-red-500 text-xs"
                />
              </div>

              <Button
                type="submit"
                className="bg-black"
                disabled={isSubmitting}
              >
                S'inscrire
              </Button>
            </form>
          )}
        </Formik>
      </div>
    </>
  );
};

export default SignupForm;
