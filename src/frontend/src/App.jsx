import {useEffect, useState} from "react";
import './App.css';
import CategoryList from "./components/CategoryList.jsx";
import MainPage from "./components/MainPage.jsx";
import {Route, Routes} from "react-router-dom";
import SearchList from "./components/SearchList.jsx";
import SignInPage from "./components/SignInPage.jsx"
import SignUpPage from "./components/SignUpPage.jsx"
import UserProfile from "./components/profile/UserProfile.jsx";
import ProductsList from "./components/ProductsList.jsx";
import ProductPage from "./components/ProductPage.jsx";
import FilterComponent from "./components/FilterComponent.jsx";
import EditProductPage from "./components/edit-product/EditProductPage.jsx";
import AddProductPage from "./components/create-product/AddProductPage.jsx";
import OrderHistory from "./components/profile/order-history/OrderHistory.jsx";
import OrderDetails from "./components/profile/order-history/OrderDetails.jsx";

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
          <Route path="/sign-in" element={<SignInPage />} />
          <Route path="/sign-up" element={<SignUpPage />} />

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

          <Route path="/product/:id" element={
              <MainPage children={<ProductPage />} />
          }/>
          <Route path="/order-details/:orderId" element={
           <MainPage children={<OrderDetails />} />
          }/>
      </Routes>
  );
}

export default App;
