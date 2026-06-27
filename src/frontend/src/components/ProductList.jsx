import '../App.css';
import {useParams} from 'react-router-dom';

function ProductList({allProducts}){
    const {categoryName} = useParams();
    const productsRequested = categoryName ? allProducts.filter(product => product.categoryName.toLowerCase() === categoryName.toLowerCase()) : allProducts;

    return (
        <div id="main-box">
            {productsRequested.map((product) => (
                <div key={product.productId} className="product">
                    <img src={product.imgUrl} alt=""/>
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

export default ProductList;