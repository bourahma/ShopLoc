import React, { useState } from "react";
import { Button, Label, TextInput } from "flowbite-react";
import loginService from "../services/login";
import { useNavigate } from "react-router-dom";

const LoginForm = () => {
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

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    loginService
      .login(formData)
      .then((data) => {
        console.log(data);
        window.localStorage.setItem("userToken", JSON.stringify(data));
        navigate("/");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="flex justify-center">
      <form className="flex max-w-md flex-col gap-4" onSubmit={handleSubmit}>
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
        <Button type="submit">Connexion</Button>
      </form>
    </div>
  );
};

export default LoginForm;
