import '../App.css';
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";

function ProductsList({allProducts}){
    const [sortWith, setSortValue] = useState(() => {
        return localStorage.getItem("sort") || "default";
    });

    const [minValue, setMinValue] = useState(() => {
        return localStorage.getItem("min") || 0;
    });

    const [maxValue, setMaxValue] = useState(() => {
        return localStorage.getItem("max") || Infinity;
    });

    useEffect(() => {
        const handleFilterValueChange = () => {
            setSortValue(localStorage.getItem("sort") || "default");
            setMinValue(localStorage.getItem("min") || 0);
            setMaxValue(localStorage.getItem("max") || Infinity);
        };

        window.addEventListener("sort_changed", handleFilterValueChange);
        return () => {
            window.removeEventListener("sort_changed", handleFilterValueChange);
        };
    }, [sortWith, minValue, maxValue]);

    const filteredProducts = [...allProducts].filter((product) => {
        if (minValue === 0 && maxValue === Infinity) {
            return true;
        }

        if (maxValue < minValue){
            return false;
        }

        return product.price >= minValue && product.price <= maxValue;
    });

    const sortedProducts = filteredProducts.sort((a, b) => {
        if (sortWith === "price increase"){
            return a.price - b.price;
        }

        if (sortWith === "price decrease"){
            return b.price - a.price;
        }

        if (sortWith === "default"){
            return 0;
        }
    });

    const getImageUrl = (url) => {
        if (!url) return 'http://localhost:8080/photos/No-image-placeholder.png';
        if (url.startsWith('http')) return url;
        return `http://localhost:8080${url}`;
    };

    return (
        <div id="main-box">
            {sortedProducts.map((product) => (
                <Link
                    to={`/product/${product.productId}`}
                    key={product.productId}
                    className="product-link"
                    style={{ textDecoration: 'none', color: 'inherit' }}
                >
                    <div key={product.productId} className="product">
                        <img src={getImageUrl(product.imageUrls?.[0])} alt={product.productName}/>
                        <p className="productName">{product.productName}</p>
                        <p className="price">Price: {product.price}$</p>
                        <p className="quantity">Quantity: {product.quantity}</p>
                        <p className="category">Category: {product.categoryName}</p>
                        <p className="seller">Seller: {product.sellerName}</p>
                    </div>

                </Link>
            ))}
        </div>
    );
}

export default ProductsList;
