import '../App.css';
import {useParams} from 'react-router-dom';

function SearchList({allProducts}){
    const {query} = useParams();
    const productsRequested = allProducts.filter(product => (product.categoryName.toLowerCase().includes(query.toLowerCase()) || product.productName.toLowerCase().includes(query.toLowerCase())));

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

export default SearchList;