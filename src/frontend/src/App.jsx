import {useEffect, useState} from "react";
import './App.css';

function App() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
      const fetchInitialData = async () => {
          try {
              const [categoryResponse, productsResponse] = await Promise.all([
                  fetch('http://localhost:8080/categories'),
                  fetch('http://localhost:8080/products')
              ]);

              const categoriesData = await categoryResponse.json();
              const productsData = await productsResponse.json();

              setCategories(categoriesData);
              setProducts(productsData);
          } catch (error) {
              console.error("Error fetching data:", error);
          }
      };

      fetchInitialData();
  }, []);

  return (
      <div>
        <nav id="navbar">
          <img src="/images/dark-logo.png" alt=""/>
          <input type="text" id="search-item" name="search" placeholder="Search Amazon ..."/>
          <div className="navbar-btns">
            <button className="sign-in">Sign In</button>
            <button className="sign-up">Sign Up</button>
          </div>
        </nav>

        <div id="categories">
            {categories.map((category) => {
                return (
                    <a className="category">{category.categoryName}</a>
                );
            })}
        </div>

        <div id="main-box">
          {products.map((product) => {
            return (
                <div key={product.productId} className="product">
                    <img src={product.imgUrl} alt=""/>
                    <p class="productName">{product.productName}</p>
                    <p class="price">Price: {product.price}$</p>
                    <p class="quantity">Quantity: {product.quantity}</p>
                    <p class="category">Category: {product.categoryName}</p>
                    <p class="seller">Seller: {product.sellerName}</p>
                </div>
            );
          })}
        </div>

          <div id="footer">

          </div>
      </div>
  );
}

export default App;
