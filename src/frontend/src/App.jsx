import {useEffect, useState} from "react";
import './App.css';
import CategoryList from "./components/CategoryList.jsx";
import MainPage from "./components/MainPage.jsx";
import {Route, Routes} from "react-router-dom";
import SearchList from "./components/SearchList.jsx";
import UserProfile from "./components/UserProfile.jsx";

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
              <MainPage children={<CategoryList allProducts={products} />} />
          }/>

          <Route path="/category/:categoryName" element={
              <MainPage children={<CategoryList allProducts={products} />} />
          }/>

          <Route path="/search/:query" element={
              <MainPage children={<SearchList allProducts={products} />} />
          }/>

          <Route path="/profile" element={
              <MainPage children={<UserProfile />} />
          }/>
      </Routes>
  );
}

export default App;
