import {useEffect, useState} from "react";
import './index.css';

function App() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/products')
        .then(data => data.json())
        .then(fetchedProducts => {
            console.log(fetchedProducts);
            setProducts(fetchedProducts);
        })
        .catch(() => alert("Products not found"));
  }, []);

  return (
      <div>
        <nav id="navbar">
          <img src="../public/images/light-logo.png" alt=""/>
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
                    <img src={product.imgUrl} alt={product.productName}/>
                    <p>Description: {product.description}</p>
                    <p>Price: {product.price}</p>
                    <p>Quantity: {product.quantity}</p>
                    <p>Category: {product.categoryName}</p>
                    <p>Seller: {product.sellerName}</p>
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
