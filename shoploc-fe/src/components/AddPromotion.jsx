import React, { useState } from "react";
import {
  Alert,
  Button,
  Label,
  TextInput,
  Datepicker,
  Textarea,
} from "flowbite-react";
import * as Yup from "yup";
import { Formik, ErrorMessage, Field } from "formik";
import usePromotions from "../hooks/usePromotions";
import { useParams } from "react-router-dom";

const AddPromotion = () => {
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");
  const token = localStorage.getItem("userToken");
  const cleanedToken = JSON.parse(token);

  const commerceId = useParams().commerceId;

  const initialValues = {
    label: "",
    startDate: new Date(),
    endDate: new Date(),
    description: "",
    discountPercent: 0,
    promotionType: "",
    requiredItems: 0,
    offeredItems: 0,
  };

  const useCreateDiscount = usePromotions.useCreateDiscount(
    cleanedToken,
    commerceId
  );
  const useCreateOffer = usePromotions.useCreateOffer(cleanedToken, commerceId);

  const validationSchema = Yup.object({
    label: Yup.string().required("Libellé requis"),
    description: Yup.string().required("Description requise"),
    promotionType: Yup.string().required("Type de promotion requis"),
  });

  const registerPromotion = (values) => {
    console.log(values);
    if (values.promotionType === "discount") {
      useCreateDiscount.mutate(values, {
        onSuccess: () => {
          setSuccess("Promotion ajoutée avec succès");
        },
        onError: (error) => {
          setError(error);
        },
      });
    } else {
      useCreateOffer.mutate(values, {
        onSuccess: () => {
          setSuccess("Promotion ajoutée avec succès");
        },
        onError: (error) => {
          setError(error);
        },
      });
    }
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
      {error && <Alert type="error">{error.message}</Alert>}
      {success && <Alert type="success">{success}</Alert>}
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={(values, { setSubmitting, resetForm }) => {
          registerPromotion(values);
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
          setFieldValue,
        }) => (
          <form
            onSubmit={handleSubmit}
            className="flex flex-col md:flex-row md:space-x-4 my-6 mx-12"
          >
            <div className="flex-1">
              <div className="mb-2 block">
                <Label htmlFor="label">Libellé</Label>
                <TextInput
                  id="label"
                  type="text"
                  placeholder="Entrez le libellé de la promotion"
                  value={values.label}
                  error={errors.label}
                  fieldtouched={touched.label?.toString()}
                  onChange={handleChange}
                />
                <ErrorMessage
                  name="label"
                  component="div"
                  className="text-red-500 text-xs"
                />
              </div>
              <div className="mb-2 block">
                <Label htmlFor="startDate">Date de début</Label>
                <Datepicker
                  id="startDate"
                  onSelectedDateChanged={(date) => {
                    setFieldValue("startDate", date, true);
                  }}
                  language="fr-FR"
                  labelClearButton="Effacer"
                  labelTodayButton="Aujourd'hui"
                />
                <ErrorMessage
                  name="startDate"
                  component="div"
                  className="text-red-500 text-xs"
                />
              </div>
              <div className="mb-2 block">
                <Label htmlFor="endDate">Date de fin</Label>
                <Datepicker
                  id="endDate"
                  onSelectedDateChanged={(date) => {
                    setFieldValue("endDate", date, true);
                  }}
                  language="fr-FR"
                  labelClearButton="Effacer"
                  labelTodayButton="Aujourd'hui"
                />
                <ErrorMessage
                  name="endDate"
                  component="div"
                  className="text-red-500 text-xs"
                />
              </div>
              <div className="mb-2 block">
                <Label htmlFor="description">Description</Label>
                <Textarea
                  id="description"
                  type="text"
                  placeholder="Entrez la description de la promotion"
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

              <div id="my-radio-group" className="mb-2 block">
                Type de Promotion
              </div>
              <div
                role="group"
                aria-labelledby="my-radio-group"
                className="flex items-center gap-2"
              >
                <label>
                  <Field type="radio" name="promotionType" value="discount" />
                  Remise
                </label>
                <label>
                  <Field type="radio" name="promotionType" value="offer" />
                  Offre
                </label>
              </div>

              {values.promotionType === "offer" && (
                <div>
                  <div className="mb-2 mt-2 block">
                    <Label htmlFor="requiredItems">
                      Nombre d'articles requis
                    </Label>
                    <TextInput
                      id="requiredItems"
                      type="number"
                      value={values.requiredItems}
                      error={errors.requiredItems}
                      fieldtouched={touched.requiredItems?.toString()}
                      onChange={handleChange}
                    />
                    <ErrorMessage
                      name="requiredItems"
                      component="div"
                      className="text-red-500 text-xs"
                    />
                  </div>
                  <div className="mb-2 block">
                    <Label htmlFor="offeredItems">
                      Nombre d'articles offerts
                    </Label>
                    <TextInput
                      id="offeredItems"
                      type="number"
                      value={values.offeredItems}
                      error={errors.offeredItems}
                      fieldtouched={touched.offeredItems?.toString()}
                      onChange={handleChange}
                    />
                    <ErrorMessage
                      name="offeredItems"
                      component="div"
                      className="text-red-500 text-xs"
                    />
                  </div>
                </div>
              )}
              {values.promotionType === "discount" && (
                <div className="mb-2 mt-2 block">
                  <Label htmlFor="discountPercent">Pourcentage de remise</Label>
                  <TextInput
                    id="discountPercent"
                    type="number"
                    placeholder="Ex: 10 pour 10%"
                    value={values.discountPercent}
                    error={errors.discountPercent}
                    fieldtouched={touched.discountPercent?.toString()}
                    onChange={handleChange}
                  />
                  <ErrorMessage
                    name="discountPercent"
                    component="div"
                    className="text-red-500 text-xs"
                  />
                </div>
              )}
              <Button className="mt-2 " type="submit">
                Ajouter la promotion
              </Button>
            </div>
          </form>
        )}
      </Formik>
    </div>
  );
};

export default AddPromotion;
