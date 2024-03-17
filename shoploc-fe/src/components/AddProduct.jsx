import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import commerceService from "../services/commerce";
import { Formik, ErrorMessage } from "formik";
import * as Yup from "yup";
import {
  Label,
  TextInput,
  Select,
  Button,
  Alert,
  Textarea,
} from "flowbite-react";

const AddProduct = () => {
  const [error, setError] = useState(null);
  const token = localStorage.getItem("userToken");
  const cleanedToken = token ? token.replace(/['"]+/g, "") : null;

  useEffect(() => {
    // need to fetch the categories and promotions from the server
  }, [cleanedToken]);

  const initialProduct = {
    productName: "",
    description: "",
    price: 0,
    quantity: 0,
    rewardPointsPrice: 0,
    gift: false,
    productCategoryLabel: "",
    multipartFile: null,
    productCategoryId: "",
    commerceId: "",
    discountId: "",
    promotion: "",
    promotionId: "",
  };

  const navigate = useNavigate();

  const registerProduct = (values) => {
    const formData = new FormData();
    formData.append(
      "productDto",
      new Blob(
        [
          JSON.stringify({
            productName: values.productName,
            description: values.description,
            price: values.price,
            quantity: values.quantity,
            rewardPointsPrice: values.rewardPointsPrice,
            gift: values.gift,
            productCategoryLabel: values.productCategoryLabel,
            productCategoryId: values.productCategoryId,
            commerceId: values.commerceId,
            discountId: values.discountId,
            promotion: values.promotion,
            promotionId: values.promotionId,
          }),
        ],
        {
          type: "application/json",
        }
      )
    );
    formData.append("multipartFile", values.multipartFile);

    commerceService
      .addProduct(formData, cleanedToken, values.commerceId)
      .then((data) => {
        console.log(data);
        navigate("/merchant/home");
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
          initialValues={initialProduct}
          validationSchema={Yup.object({
            productName: Yup.string().required("Required"),
            description: Yup.string().required("Required"),
            price: Yup.number().required("Required"),
            quantity: Yup.number().required("Required"),
            rewardPointsPrice: Yup.number().required("Required"),
            gift: Yup.boolean().required("Required"),
            productCategoryLabel: Yup.string().required("Required"),
            productCategoryId: Yup.string().required("Required"),
            commerceId: Yup.string().required("Required"),
            discountId: Yup.string().required("Required"),
            promotion: Yup.string().required("Required"),
            promotionId: Yup.string().required("Required"),
            multipartFile: Yup.mixed().required("Required"),
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
            <form onSubmit={handleSubmit}>
              <div className="flex max-w-full w-full flex-col gap-4">
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

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="multipartFile">Image</Label>
                  </div>
                  <TextInput
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

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="rewardPointsPrice">
                      Prix en points de récompense
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

                  <ErrorMessage
                    name="gift"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="productCategoryLabel">Catégorie</Label>
                  </div>
                  <TextInput
                    id="productCategoryLabel"
                    type="text"
                    placeholder="Catégorie"
                    value={values.productCategoryLabel}
                    error={errors.productCategoryLabel}
                    fieldtouched={touched.productCategoryLabel?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="productCategoryLabel"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="productCategoryId">ID Catégorie</Label>
                  </div>
                  <TextInput
                    id="productCategoryId"
                    type="text"
                    placeholder="ID Catégorie"
                    value={values.productCategoryId}
                    error={errors.productCategoryId}
                    fieldtouched={touched.productCategoryId?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="productCategoryId"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="commerceId">ID Commerce</Label>
                  </div>
                  <TextInput
                    id="commerceId"
                    type="text"
                    placeholder="ID Commerce"
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

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="discountId">ID Réduction</Label>
                  </div>
                  <TextInput
                    id="discountId"
                    type="text"
                    placeholder="ID Réduction"
                    value={values.discountId}
                    error={errors.discountId}
                    fieldtouched={touched.discountId?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="discountId"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="promotion">Promotion</Label>
                  </div>
                  <TextInput
                    id="promotion"
                    type="text"
                    placeholder="Promotion"
                    value={values.promotion}
                    error={errors.promotion}
                    fieldtouched={touched.promotion?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="promotion"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <div>
                  <div className="mb-2 block">
                    <Label htmlFor="promotionId">ID Promotion</Label>
                  </div>
                  <TextInput
                    id="promotionId"
                    type="text"
                    placeholder="ID Promotion"
                    value={values.promotionId}
                    error={errors.promotionId}
                    fieldtouched={touched.promotionId?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="promotionId"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>

                <Button
                  className="mb-2 block bg-shopred w-full justify-center items-center"
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
    </>
  );
};

export default AddProduct;
