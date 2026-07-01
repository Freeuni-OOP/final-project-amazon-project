import '../App.css';
import {useParams} from 'react-router-dom';
import {useEffect, useState} from "react";

function CategoryList(){
    const {categoryName} = useParams();
    const [productsRequested, setProductsRequested] = useState([]);

    useEffect(() => {
        const fetchInitialData = async () => {
            try {
                const [productsResponse] = await Promise.all([
                    fetch(`http://localhost:8080/products/category-name/${categoryName}`)
                ]);

                const productsData = await productsResponse.json();
                setProductsRequested(productsData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchInitialData();
    }, [categoryName]);

    return (
        <div id="main-box">
            {productsRequested.map((product) => (
                <div key={product.productId} className="product">
                    <img src={product.imageUrls?.[0] || "http://localhost:8080/photos/No-image-placeholder.png"} alt=""/>
                    <p className="productName">{product.productName}</p>
                    <p className="price">Price: {product.price}$</p>
                    <p className="quantity">Quantity: {product.quantity}</p>
                    <p className="category">Category: {product.categoryName}</p>
                    <p className="seller">Seller: {product.sellerName}</p>
                </div>
            ))}
        </div>
    );
}

export default CategoryList;