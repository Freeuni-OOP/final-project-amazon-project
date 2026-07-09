import {useEffect, useState} from "react";
import './App.css';
import CategoryList from "./components/CategoryList.jsx";
import MainPage from "./components/MainPage.jsx";
import {Route, Routes} from "react-router-dom";
import SearchList from "./components/SearchList.jsx";
import ProductsList from "./components/ProductsList.jsx";
import ProductPage from "./components/ProductPage.jsx";

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
              <MainPage requestedProducts={<ProductsList allProducts={products} />} />
          }/>

          <Route path="/category-name/:categoryName" element={
              <MainPage requestedProducts={<CategoryList/>} />
          }/>

          <Route path="/search/:query" element={
              <MainPage requestedProducts={<SearchList allProducts={products} />} />
          }/>

          <Route path="/product/:id" element={
              <MainPage requestedProducts={<ProductPage />} />
          }/>
      </Routes>
  );
}

export default App;
