import {useEffect, useState} from "react";
import './App.css';
import ProductList from "./components/ProductList.jsx";
import MainPage from "./components/MainPage.jsx";
import {Route, Routes} from "react-router-dom";

function App() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
      const fetchInitialData = async () => {
          try {
              const [productsResponse] = await Promise.all([
                  fetch('http://localhost:8080/products')
              ]);

              const productsData = await productsResponse.json();
              setProducts(productsData);
          } catch (error) {
              console.error("Error fetching data:", error);
          }
      };

      fetchInitialData();
  }, []);

  return (
      <Routes>
          <Route path="/" element={
              <MainPage requestedProducts={<ProductList allProducts={products} />} />
          }/>

          <Route path="/category/:categoryName" element={
              <MainPage requestedProducts={<ProductList allProducts={products} />} />
          }/>
      </Routes>
  );
}

export default App;
