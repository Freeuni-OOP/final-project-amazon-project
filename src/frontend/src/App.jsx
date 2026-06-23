import {useEffect, useState} from "react";
import './App.css';

function App() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/products')
        .then(data => data.json())
        .then(fetchedProducts => {
            setProducts(fetchedProducts);
        })
        .catch(() => alert("Products not found"));
  }, []);

  return (
      <div>
        <nav id="navbar">
          <img src="../public/images/dark-logo.png" alt=""/>
          <input type="text" id="search-item" name="search" placeholder="Search Amazon ..."/>
          <div className="navbar-btns">
            <button className="sign-in">Sign In</button>
            <button className="sign-up">Sign Up</button>
          </div>
        </nav>

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
