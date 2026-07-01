import '../App.css';

function ProductsList({allProducts}){
    return (
        <div id="main-box">
            {allProducts.map((product) => (
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

export default ProductsList;