import React, { useEffect, useState } from "react";
import { Alert, Button, Label, Select, TextInput } from "flowbite-react";
import { useNavigate } from "react-router-dom";
import { ErrorMessage, Formik } from "formik";
import * as Yup from "yup";
import registerMerchantService from "../services/registerMerchant";
import commerceService from "../services/commerce";

const MerchantRegistrationForm = () => {
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [commerces, setCommerces] = useState([]);

  const token = localStorage.getItem("userToken");
  const cleanedToken = token.replace(/['"]+/g, "");

  useEffect(() => {
    commerceService
      .fetchCommerces(cleanedToken)
      .then((data) => {
        setCommerces(data);
      })
      .catch((error) => {
        console.error("Error fetching commercants:", error);
        setError(error);
      });
  }, [cleanedToken]);

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
    console.log(values);
    registerMerchantService
      .registerMerchant(
        {
          username: values.username,
          lastname: values.lastname,
          firstname: values.firstname,
          password: values.password,
          confirmedPassword: values.confirmedPassword,
          email: values.email,
          phoneNumber: values.phoneNumber,
          subscriptionDate: null,
          commerce: null,
          commerceId: values.commerceId,
          role: null,
        },
        cleanedToken
      )
      .then((data) => {
        setSuccess("Commerçant inscrit avec succès");
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

  if (success) {
    setTimeout(() => {
      setSuccess(null);
    }, 5000);
  }

  return (
    <div>
      {error && <Alert color="failure">{error.message}</Alert>}
      {success && <Alert color="success">{success}</Alert>}
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
            commerceId: Yup.string().required("Champ requis"),
          })}
          onSubmit={(values, { setSubmitting, resetForm }) => {
            createMerchant(values);
            resetForm();
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
              onSubmit={handleSubmit}
              className="flex flex-col md:flex-row md:space-x-4 my-6 mx-12"
            >
              <div className="flex-1">
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
                    placeholder="Numéro de téléphone"
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
              <div className="flex-1">
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="password">Mot de passe</Label>
                  </div>
                  <TextInput
                    id="password"
                    type="password"
                    placeholder="Mot de passe"
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
                    placeholder="Confirmer le mot de passe"
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
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="commerceId">Commerce</Label>
                  </div>
                  <Select
                    id="commerceId"
                    value={values.commerceId}
                    error={errors.commerceId}
                    fieldtouched={touched.commerceId?.toString()}
                    onChange={handleChange}
                  >
                    <option value="">Choix du commerce</option>
                    {commerces.map((commerce) => (
                      <option
                        key={commerce.commerceId}
                        value={commerce.commerceId}
                      >
                        {commerce.commerceName}
                      </option>
                    ))}
                  </Select>

                  <ErrorMessage
                    name="commerceId"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <Button
                  className="mt-2 bg-shopred w-full justify-center items-center"
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
    </div>
  );
};

export default MerchantRegistrationForm;
