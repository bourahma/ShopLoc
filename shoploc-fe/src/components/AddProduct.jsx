import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { Formik, ErrorMessage } from "formik";
import productServices from "../services/product";
import * as Yup from "yup";
import {
  Label,
  TextInput,
  Select,
  Button,
  Alert,
  Textarea,
  FileInput,
} from "flowbite-react";
import useProducts from "../hooks/useProducts";

const AddProduct = () => {
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const token = localStorage.getItem("userToken");
  const cleanedToken = JSON.parse(token);
  const commerceId = useParams().commerceId;

  const commerceCategoriesResponse = useProducts.useProductsCategories(
    cleanedToken,
    commerceId
  );

  const initialProduct = {
    productName: "",
    description: "",
    price: 0,
    quantity: 0,
    rewardPointsPrice: 0,
    view: 0,
    gift: false,
    productCategoryId: "",
    multipartFile: null,
  };

  const registerProduct = (values) => {
    const formData = new FormData();
    formData.append(
      "productDTO",
      new Blob(
        [
          JSON.stringify({
            productName: values.productName,
            description: values.description,
            price: values.price,
            quantity: values.quantity,
            rewardPointsPrice: values.rewardPointsPrice,
            gift: values.gift,
            productCategoryId: values.productCategoryId,
            view: 0,
            commerceId: commerceId,
          }),
        ],
        {
          type: "application/json",
        }
      )
    );
    formData.append("multipartFile", values.multipartFile);

    productServices
      .addProduct(formData, cleanedToken, commerceId)
      .then((data) => {
        setSuccess("Produit ajouté avec succès");
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
          initialValues={initialProduct}
          validationSchema={Yup.object({
            productName: Yup.string().required("Requis"),
            description: Yup.string().required("Requis"),
            price: Yup.number()
              .required("Requis")
              .positive("Le prix doit être positif"),
            quantity: Yup.number()
              .required("Required")
              .positive("La quantité doit être positive"),
            rewardPointsPrice: Yup.number()
              .required("Required")
              .positive("Le prix en points de récompense doit être positif"),
            gift: Yup.boolean().required("Requis"),
            productCategoryId: Yup.string().required("Requis"),
          })}
          onSubmit={(values, { setSubmitting, resetForm }) => {
            registerProduct(values);
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
                    <Label htmlFor="productName">Nom du produit</Label>
                  </div>
                  <TextInput
                    id="productName"
                    type="text"
                    placeholder="Nom du produit"
                    value={values.productName}
                    error={errors.productName}
                    fieldtouched={touched.productName?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="productName"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="quantity">Quantité</Label>
                  </div>
                  <TextInput
                    id="quantity"
                    type="number"
                    placeholder="Quantité"
                    value={values.quantity}
                    error={errors.quantity}
                    fieldtouched={touched.quantity?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="quantity"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="price">Prix</Label>
                  </div>
                  <TextInput
                    id="price"
                    type="number"
                    placeholder="Prix"
                    value={values.price}
                    error={errors.price}
                    fieldtouched={touched.price?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="price"
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
                    type="text"
                    placeholder="Description"
                    value={values.description}
                    error={errors.description}
                    fieldtouched={touched.description?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="description"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
              </div>

              <div className="flex-1">
                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="rewardPointsPrice">
                      Points de récompense
                    </Label>
                  </div>
                  <TextInput
                    id="rewardPointsPrice"
                    type="number"
                    placeholder="Prix en points de récompense"
                    value={values.rewardPointsPrice}
                    error={errors.rewardPointsPrice}
                    fieldtouched={touched.rewardPointsPrice?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="rewardPointsPrice"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="gift">Cadeau</Label>
                  </div>
                  <Select
                    id="gift"
                    value={values.gift}
                    error={errors.gift}
                    fieldtouched={touched.gift?.toString()}
                    onChange={handleChange}
                  >
                    <option value="true">Oui</option>
                    <option value="false">Non</option>
                  </Select>
                  <ErrorMessage
                    name="gift"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="productCategoryId">Catégorie</Label>
                  </div>
                  <Select
                    id="productCategoryId"
                    value={values.productCategoryId}
                    error={errors.productCategoryId}
                    fieldtouched={touched.productCategoryId?.toString()}
                    onChange={handleChange}
                  >
                    <option value="">Choisir une catégorie</option>
                    {commerceCategoriesResponse.data?.map((category) => (
                      <option
                        key={category.productCategoryId}
                        value={category.productCategoryId}
                      >
                        {category.label}
                      </option>
                    ))}
                  </Select>
                  <ErrorMessage
                    name="productCategoryId"
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
                  className="mt-2 block bg-shopred w-full justify-center items-center"
                  type="submit"
                  disabled={isSubmitting}
                >
                  Ajouter
                </Button>
              </div>
            </form>
          )}
        </Formik>
      </div>
    </div>
  );
};

export default AddProduct;
