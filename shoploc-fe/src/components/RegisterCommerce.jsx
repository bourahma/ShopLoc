import React, { useEffect, useState } from "react";
import { Alert, Button, Label, Select, TextInput } from "flowbite-react";
import { useNavigate } from "react-router-dom";
import registerCommerceService from "../services/registerCommerce";
import fetchCommerceTypes from "../services/commerceTypesService";
import { ErrorMessage, Formik } from "formik";
import * as Yup from "yup";

const CommerceRegistrationForm = () => {
  const [error, setError] = useState(null);
  const [commerceTypes, setCommerceTypes] = useState([]);
  const token = localStorage.getItem("userToken");
  const cleanedToken = token ? token.replace(/['"]+/g, "") : null;

  useEffect(() => {
    fetchCommerceTypes(cleanedToken)
      .then((data) => {
        console.log(data);
        setCommerceTypes(data);
      })
      .catch((error) => {
        console.error("Error fetching commerce types: ", error);
      });
  }, [cleanedToken]);

  const initialCommerce = {
    commerceName: "",
    openingHour: "",
    closingHour: "",
    multipartFile: "",
    street: "",
    city: "",
    postalCode: "",
    commerceType: "",
  };

  const navigate = useNavigate();

  const registerCommerce = (values) => {
    console.log(values);
    registerCommerceService
      .registerCommerce(
        {
          commerceName: values.commerceName,
          openingHour: values.openingHour,
          closingHour: values.closingHour,
          multipartFile: values.multipartFile,
          addressDTO: {
            street: values.street,
            city: values.city,
            postalCode: values.postalCode,
            latitude: 0.0,
            longitude: 0.0,
          },
          disabled: false,
          commerceType: {
            commerceTypeId: values.commerceType,
          },
        },
        cleanedToken
      )
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
            closingHour: Yup.string()
              .required("Champ requis")
              .test(
                "is-greater",
                "L'heure de fermeture doit être supérieure à l'heure d'ouverture",
                function (value) {
                  const { openingHour } = this.parent;
                  return value > openingHour;
                }
              ),
            street: Yup.string().required("Champ requis"),
            city: Yup.string().required("Champ requis"),
            postalCode: Yup.string().required("Champ requis"),
            commerceType: Yup.string().required("Champ requis"),
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
                    <Label htmlFor="street">Rue</Label>
                  </div>
                  <TextInput
                    id="street"
                    type="text"
                    placeholder="Rue"
                    value={values.street}
                    error={errors.street}
                    fieldtouched={touched.street?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="street"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="city">Ville</Label>
                  </div>
                  <TextInput
                    id="city"
                    type="text"
                    placeholder="Ville"
                    value={values.city}
                    error={errors.city}
                    fieldtouched={touched.city?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="city"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="postalCode">Code postal</Label>
                  </div>
                  <TextInput
                    id="postalCode"
                    type="text"
                    placeholder="Code postal"
                    value={values.postalCode}
                    error={errors.postalCode}
                    fieldtouched={touched.postalCode?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="postalCode"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="commerceType">Type de commerce</Label>
                  </div>
                  <Select
                    id="commerceType"
                    error={errors.commerceType}
                    fieldtouched={touched.commerceType?.toString()}
                    onChange={handleChange}
                  >
                    <option value="" disabled>
                      Choisir un type de commerce
                    </option>
                    {commerceTypes.map((type) => (
                      <option
                        key={type.commerceTypeId}
                        value={type.commerceTypeId}
                      >
                        {type.label}
                      </option>
                    ))}
                  </Select>
                  <ErrorMessage
                    name="commerceType"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="multipartFile">Image</Label>
                  </div>
                  <TextInput
                    id="multipartFile"
                    type="file"
                    placeholder="Image"
                    accept="image/*"
                    value={values.multipartFile}
                    error={errors.multipartFile}
                    fieldtouched={touched.multipartFile?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="multipartFile"
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
