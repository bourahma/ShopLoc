import React, { useState } from "react";
import { Alert, Button, Label, TextInput } from "flowbite-react";
import { useNavigate } from "react-router-dom";
import registerCommerceService from "../services/registerCommerce";
import { ErrorMessage, Formik } from "formik";
import * as Yup from "yup";

const CommerceRegistrationForm = () => {
  const [error, setError] = useState(null);

  const initialCommerce = {
    commerceName: "",
    openingHour: "",
    closingHour: "",
    imageUrl: "",
  };

  const navigate = useNavigate();

  const registerCommerce = (values) => {
    delete values.agree;
    registerCommerceService
      .registerCommerce(values)
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
      <div className="flex flex-wrap justify-center my-6 mx-12">
        <Formik
          initialValues={initialCommerce}
          validationSchema={Yup.object({
            commerceName: Yup.string()
              .max(50, "Doit être 50 caractères ou moins")
              .required("Champ requis"),
            openingHour: Yup.string().required("Champ requis"),
            closingHour: Yup.string().required("Champ requis"),
            imageUrl: Yup.string().required("Champ requis"),
          })}
          onSubmit={(values, { setSubmitting }) => {
            registerCommerce(values);
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
            <form onSubmit={handleSubmit}>
              <div className="flex max-w-full w-full flex-col gap-4">
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="commerceName">Nom du commerce</Label>
                  </div>
                  <TextInput
                    id="commerceName"
                    type="text"
                    placeholder="Nom du commerce"
                    value={values.commerceName}
                    error={errors.commerceName}
                    fieldtouched={touched.commerceName?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="commerceName"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="openingHour">Heure d'ouverture</Label>
                  </div>
                  <TextInput
                    id="openingHour"
                    type="time"
                    placeholder="Heure d'ouverture"
                    value={values.openingHour}
                    error={errors.openingHour}
                    fieldtouched={touched.openingHour?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="openingHour"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="closingHour">Heure de fermeture</Label>
                  </div>
                  <TextInput
                    id="closingHour"
                    type="time"
                    placeholder="Heure de fermeture"
                    value={values.closingHour}
                    error={errors.closingHour}
                    fieldtouched={touched.closingHour?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="closingHour"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="imageUrl">URL de l'image</Label>
                  </div>
                  <TextInput
                    id="imageUrl"
                    type="text"
                    placeholder="URL de l'image"
                    value={values.imageUrl}
                    error={errors.imageUrl}
                    fieldtouched={touched.imageUrl?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="imageUrl"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <Button
                  className="mb-2 block bg-shopred w-full justify-center items-center"
                  type="submit"
                  disabled={isSubmitting}
                >
                  Inscrire le commerce
                </Button>
              </div>
            </form>
          )}
        </Formik>
      </div>
    </>
  );
};

export default CommerceRegistrationForm;
