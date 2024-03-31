import React from "react";
import { useState } from "react";
import { TextInput, Button, Label, Alert, Textarea } from "flowbite-react";
import { Formik, ErrorMessage } from "formik";
import * as Yup from "yup";
import useProducts from "../hooks/useProducts";

const AddCategory = ({ commerceId }) => {
  const [success, setSuccess] = useState(null);
  const [error, setError] = useState(null);
  const token = localStorage.getItem("userToken");
  const cleanedToken = token ? token.replace(/['"]+/g, "") : null;

  console.log("commerceId", commerceId);

  const initialCategory = {
    label: "",
    description: "",
  };

  const createProductCategoryMutation = useProducts.useCreateProductCategory(
    cleanedToken,
    commerceId
  );

  const addCategory = (values) => {
    createProductCategoryMutation.mutate(values, {
      onSuccess: () => {
        console.log("Category added successfully");
        setSuccess("Catégorie ajoutée avec succès");
        setTimeout(() => {
          setSuccess(null);
        }, 5000);
      },
      onError: (error) => {
        console.log("Error adding category", error);
        setError(error);
        setTimeout(() => {
          setError(null);
          createProductCategoryMutation.reset();
        }, 5000);
      },
    });
  };

  return (
    <div>
      {error && <Alert color="failure">{error.message}</Alert>}
      {success && <Alert color="success">{success}</Alert>}
      <div>
        <Formik
          initialValues={initialCategory}
          validationSchema={Yup.object({
            label: Yup.string().required("Required"),
            description: Yup.string().required("Required"),
          })}
          onSubmit={(values, { setSubmitting, resetForm }) => {
            addCategory(values);
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
              className="flex flex-col my-6 mx-12 md:flex-row md:space-x-4"
            >
              <div className="flex-1/2">
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="label">Nom de la catégorie</Label>
                  </div>
                  <TextInput
                    id="label"
                    value={values.label}
                    onChange={handleChange}
                    fieldtouched={touched.label?.toString()}
                    errors={errors.label}
                  />
                  <ErrorMessage
                    name="label"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="description">Description</Label>
                  </div>

                  <Textarea
                    id="description"
                    value={values.description}
                    onChange={handleChange}
                    fieldtouched={touched.description?.toString()}
                    errors={errors.description}
                  />

                  <ErrorMessage
                    name="description"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <Button
                  type="submit"
                  disabled={isSubmitting}
                  className="mt-2 block bg-shopred w-full justify-center"
                >
                  Ajouter Catégorie
                </Button>
              </div>
            </form>
          )}
        </Formik>
      </div>
    </div>
  );
};

export default AddCategory;
