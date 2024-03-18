import { useEffect, useState } from "react";
import commerceService from "../services/commerce";
import productSample from "../images/productSample.png";
import { jwtDecode } from "jwt-decode";
import { Card, Button, Dropdown } from "flowbite-react";

const MerchantProducts = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [products, setProducts] = useState([]);
  const token = localStorage.getItem("userToken");
  const cleanedToken = token ? token.replace(/['"]+/g, "") : null;

  // Decode the token
  let decoded = jwtDecode(cleanedToken);

  // Get the merchant ID
  let merchantId = decoded.userId;

  useEffect(() => {
    commerceService
      .fetchMerchantProducts(cleanedToken, merchantId)
      .then((data) => {
        setProducts(data);
      })
      .catch((error) => {
        console.error("Error fetching merchant products: ", error);
      });
  }, [cleanedToken, merchantId]);

  const filteredProducts = products.filter((product) =>
    product.productName.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    // list all the products
    <div>
      <div className="flex flex-wrap gap-4">
        <input
          type="text"
          placeholder="Rechercher un produit"
          className="border-2 border-gray-300 p-2"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
      </div>
      <div className="flex flex-col w-2/3 gap-4">
        {filteredProducts.map((product) => (
          //put them inside card components with edit details and delete buttons
          <Card
            key={product.productId}
            className="bg-gray-100 max-w-sm"
            imgAlt="product image"
            imgSrc={productSample}
          >
            <div className="flex justify-end px-4 pt-4">
              <Dropdown inline label="">
                <Dropdown.Item>
                  <a
                    href="#"
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-600 dark:hover:text-white"
                  >
                    Modifier
                  </a>
                </Dropdown.Item>
                <Dropdown.Item>
                  <a
                    href="#"
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-600 dark:hover:text-white"
                  >
                    Détails
                  </a>
                </Dropdown.Item>
                <Dropdown.Item>
                  <a
                    href="#"
                    className="block px-4 py-2 text-sm text-red-600 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-600 dark:hover:text-white"
                  >
                    Supprimer
                  </a>
                </Dropdown.Item>
              </Dropdown>
            </div>
            <h5>
              <b>{product.productName}</b>
            </h5>
            <h6>{product.price} €</h6>
            <h6>{product.quantity} produits restants</h6>
            <p className="font-normal text-gray-700 dark:text-gray-400">
              {product.description}
            </p>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default MerchantProducts;
