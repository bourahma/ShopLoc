import React from "react";
import { useFormik } from "formik";
import { TextInput, Button } from "flowbite-react";

const AddCategory = ({ commerceId }) => {
  const formik = useFormik({
    initialValues: {
      commerceId: commerceId,
      label: "",
      description: "",
    },
    onSubmit: (values) => {
      // Handle form submission here
      console.log(values);
    },
    validate: (values) => {
      const errors = {};

      if (!values.label) {
        errors.label = "Label is required";
      }

      if (!values.description) {
        errors.description = "Description is required";
      }

      return errors;
    },
  });

  return (
    <form onSubmit={formik.handleSubmit}>
      <TextInput
        type="text"
        name="label"
        value={formik.values.label}
        onChange={formik.handleChange}
        placeholder="Label"
      />
      {formik.errors.label && formik.touched.label && (
        <div>{formik.errors.label}</div>
      )}

      <TextInput
        type="text"
        name="description"
        value={formik.values.description}
        onChange={formik.handleChange}
        placeholder="Description"
      />
      {formik.errors.description && formik.touched.description && (
        <div>{formik.errors.description}</div>
      )}

      <Button type="submit">Create Category</Button>
    </form>
  );
};

export default AddCategory;
