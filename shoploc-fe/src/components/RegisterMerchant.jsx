import React, { useState } from "react";
import { Alert, Button, Label, TextInput } from "flowbite-react";
import { useNavigate } from "react-router-dom";
import { ErrorMessage, Formik } from "formik";
import * as Yup from "yup";
import registerMerchantService from "../services/registerMerchant";

const MerchantRegistrationForm = () => {
  const [error, setError] = useState(null);

  const initialMerchant = {
    username: "",
    lastname: "",
    firstname: "",
    password: "",
    confirmedPassword: "",
    email: "",
    phoneNumber: "",
    subscriptionDate: "",
    commerceId: "",
  };

  const navigate = useNavigate();

  const createMerchant = (values) => {
    delete values.agree;
    registerMerchantService
      .registerMerchant(values)
      .then((data) => {
        console.log(data);
        navigate("/admin/home");
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

  if (error) {
    setTimeout(() => {
      setError(null);
    }, 5000);
  }

  return (
    <>
      {error && <Alert color="failure">{error.message}</Alert>}
      <div>
        <Formik
          initialValues={initialMerchant}
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
            subscriptionDate: Yup.date().default(() => new Date()),
            commerceId: Yup.string().required("Champ requis"),
          })}
          onSubmit={(values, { setSubmitting }) => {
            createMerchant(values);
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
              className="flex flex-wrap justify-center gap-4 my-6 mx-6"
              onSubmit={handleSubmit}
            >
              <div className="flex max-w-md w-full flex-col gap-4">
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
              </div>
              <div className="flex max-w-md w-full flex-col gap-4">
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
                <div className="mb-2 block">
                  <Label htmlFor="subscriptionDate">Date d'inscription</Label>
                </div>
                <TextInput
                  id="subscriptionDate"
                  type="date"
                  placeholder="Date d'inscription"
                  value={values.subscriptionDate}
                  error={errors.subscriptionDate}
                  fieldtouched={touched.subscriptionDate?.toString()}
                  onChange={handleChange}
                />
                <ErrorMessage
                  name="subscriptionDate"
                  component="div"
                  className="text-red-500 text-xs"
                />
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="commerceId">ID du commerce</Label>
                  </div>
                  <TextInput
                    id="commerceId"
                    type="text"
                    placeholder="ID du commerce"
                    value={values.commerceId}
                    error={errors.commerceId}
                    fieldtouched={touched.commerceId?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="commerceId"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <Button
                  className="mb-2 block bg-shopred w-full justify-center items-center"
                  type="submit"
                  disabled={isSubmitting}
                >
                  Inscrire le commerçant
                </Button>
              </div>
            </form>
          )}
        </Formik>
      </div>
    </>
  );
};

export default MerchantRegistrationForm;
