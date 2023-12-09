import React, { useState } from "react";
import { Button, Checkbox, Label, TextInput } from "flowbite-react";
import { Link, useNavigate } from "react-router-dom";
import signupService from "../services/signup";

const SignupForm = () => {
  const [formData, setFormData] = useState({
    username: "",
    lastname: "",
    firstname: "",
    password: "",
    confirmedPassword: "",
    email: "",
    phoneNumber: "",
  });

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    signupService
      .signup(formData)
      .then((data) => {
        console.log(data);
        navigate("/login");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [id]: value,
    }));
  };

  return (
    <div className="flex justify-center">
      <form
        className="grid grid-cols-2 gap-4 max-w-2xl mx-auto"
        onSubmit={handleSubmit}
      >
        <div className="flex flex-col gap-4">
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
              <Label htmlFor="lastname">Nom de famille</Label>
            </div>
            <TextInput
              id="lastname"
              type="text"
              placeholder="Votre nom de famille"
              required
              onChange={handleChange}
            />
          </div>
          <div>
            <div className="mb-2 block">
              <Label htmlFor="firstname">Prénom</Label>
            </div>
            <TextInput
              id="firstname"
              type="text"
              placeholder="Votre prénom"
              required
              onChange={handleChange}
            />
          </div>
        </div>
        <div className="flex flex-col gap-4">
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
          <div>
            <div className="mb-2 block">
              <Label htmlFor="confirmedPassword">
                Confirmer le mot de passe
              </Label>
            </div>
            <TextInput
              id="confirmedPassword"
              type="password"
              required
              onChange={handleChange}
            />
          </div>
          <div>
            <div className="mb-2 block">
              <Label htmlFor="email">Adresse e-mail</Label>
            </div>
            <TextInput
              id="email"
              type="email"
              placeholder="addresse@gmail.com"
              required
              onChange={handleChange}
            />
          </div>
        </div>
        <div>
          <div className="mb-2 block">
            <Label htmlFor="phoneNumber">Numéro de téléphone</Label>
          </div>
          <TextInput
            id="phoneNumber"
            type="text"
            placeholder="Votre numéro de téléphone"
            required
            onChange={handleChange}
          />
        </div>
        <div className="flex items-center gap-2">
          <Checkbox id="agree" />
          <Label htmlFor="agree" className="flex">
            J'accepte les &nbsp;
            <Link
              to="#"
              className="text-cyan-600 hover:underline dark:text-cyan-400"
            >
              termes et conditions
            </Link>
          </Label>
        </div>
        <Button type="submit" className="bg-black">
          S'inscrire
        </Button>
      </form>
    </div>
  );
};

export default SignupForm;
