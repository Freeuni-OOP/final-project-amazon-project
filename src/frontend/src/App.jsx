import {useEffect, useState} from "react";
import './App.css';
import CategoryList from "./components/CategoryList.jsx";
import MainPage from "./components/MainPage.jsx";
import {Route, Routes} from "react-router-dom";
import SearchList from "./components/SearchList.jsx";
import UserProfile from "./components/UserProfile.jsx";
import ProductsList from "./components/ProductsList.jsx";
import FilterComponent from "./components/FilterComponent.jsx";
import EditProductPage from "./components/EditProductPage.jsx";
import AddProductPage from "./components/AddProductPage.jsx";

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
              <MainPage children={
                  <>
                      <FilterComponent/>
                      <ProductsList allProducts={products} />
                  </>
              } />
          }/>

          <Route path="/category-name/:categoryName" element={
              <MainPage children={
                  <>
                      <FilterComponent/>
                      <CategoryList/>
                  </>
              } />
          }/>

          <Route path="/search/:query" element={
              <MainPage children={
                  <>
                      <FilterComponent/>
                      <SearchList allProducts={products} />
                  </>
              } />
          }/>

          <Route path="/profile" element={
              <MainPage children={<UserProfile />} />
          }/>

          <Route path="/edit-product/:id" element={
              <EditProductPage />
          }/>

          <Route path="/create-product" element={
              <AddProductPage />
          }/>
      </Routes>
  );
}

export default App;
