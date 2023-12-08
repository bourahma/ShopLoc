import React, { useState } from "react";
import { Button, Label, TextInput } from "flowbite-react";
import { Link } from "react-router-dom";

const LoginForm = ({ handleSubmit }) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [id]: value,
    }));
  };

  return (
    <div className="flex justify-center gap-10">
      <form
        className="flex max-w-md flex-col gap-4"
        onSubmit={(e) => handleSubmit(e, formData)}
      >
        <div>
          <div className="mb-2 block">
            <Label htmlFor="username">Nom d'utilisateur</Label>
          </div>
          <TextInput
            id="username"
            type="text"
            placeholder="Votre nom d'utilisateur"
            required
            onChange={handleChange}
          />
        </div>
        <div>
          <div className="mb-2 block">
            <Label htmlFor="password">Mot de passe</Label>
          </div>
          <TextInput
            id="password"
            type="password"
            required
            onChange={handleChange}
          />
        </div>
        <Button type="submit" className="bg-black">
          Connexion
        </Button>
      </form>
      <div className="flex max-w-md flex-col gap-4">
        Vous n'avez pas de encore compte ? &nbsp;
        <Link to="/signup" className="text-blue-500">
          Inscrivez-vous
        </Link>
      </div>
    </div>
  );
};

export default LoginForm;
