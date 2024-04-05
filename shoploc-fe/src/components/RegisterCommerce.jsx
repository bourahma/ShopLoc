import React, { useEffect, useState } from "react";
import {
  Alert,
  Button,
  Label,
  Select,
  TextInput,
  FileInput,
} from "flowbite-react";
import { useNavigate } from "react-router-dom";
import commerceService from "../services/commerce";
import fetchCommerceTypes from "../services/commerceTypesService";
import { ErrorMessage, Formik } from "formik";
import * as Yup from "yup";

const CommerceRegistrationForm = () => {
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [commerceTypes, setCommerceTypes] = useState([]);
  const token = localStorage.getItem("userToken");
  const cleanedToken = JSON.parse(token);

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
    multipartFile: null,
    street: "",
    city: "",
    postalCode: "",
    commerceType: "",
  };

  const navigate = useNavigate();

  const registerCommerce = (values) => {
    const formData = new FormData();
    formData.append(
      "commerceDTO",
      new Blob(
        [
          JSON.stringify({
            commerceName: values.commerceName,
            openingHour: values.openingHour,
            closingHour: values.closingHour,
            disabled: false,
            addressDTO: {
              street: values.street,
              city: values.city,
              postalCode: values.postalCode,
              latitude: 0.0,
              longitude: 0.0,
            },
            commerceType: {
              commerceTypeId: values.commerceType,
            },
          }),
        ],
        {
          type: "application/json",
        }
      )
    );
    formData.append("multipartFile", values.multipartFile);

    commerceService
      .registerCommerce(formData, cleanedToken)
      .then((data) => {
        console.log(data);
        setSuccess("Commerce inscrit avec succès");
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
          onSubmit={(values, { setSubmitting, resetForm }) => {
            registerCommerce(values);
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
            setFieldValue,
          }) => (
            <form
              onSubmit={handleSubmit}
              className="flex flex-col md:flex-row md:space-x-4 my-6 mx-12"
            >
              <div className="flex-1">
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
                    <Label htmlFor="commerceType">Type de commerce</Label>
                  </div>
                  <Select
                    id="commerceType"
                    error={errors.commerceType}
                    fieldtouched={touched.commerceType?.toString()}
                    onChange={handleChange}
                  >
                    <option value="">Choix du type de commerce</option>
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
              </div>
              <div className="flex-1">
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
                    <Label htmlFor="multipartFile">Image</Label>
                  </div>
                  <FileInput
                    id="multipartFile"
                    type="file"
                    placeholder="Image"
                    accept="image/*"
                    error={errors.multipartFile}
                    fieldtouched={touched.multipartFile?.toString()}
                    onChange={(event) => {
                      setFieldValue(
                        "multipartFile",
                        event.currentTarget.files[0]
                      );
                    }}
                  />
                  <ErrorMessage
                    name="multipartFile"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <Button
                  className="mt-2 bg-shopred w-full justify-center items-center"
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
    </div>
  );
};

export default CommerceRegistrationForm;
