/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "./node_modules/flowbite-react/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        shopred: "#B24439",
        shopblue: "#3B5998",
        shopyellow: "#E5A300",
        shopgray: "#EAE7DF",
        shopgraytext: "rgba(0, 0, 0, 0.51)",
      },
    },
  },
  plugins: [require("flowbite/plugin")],
};
