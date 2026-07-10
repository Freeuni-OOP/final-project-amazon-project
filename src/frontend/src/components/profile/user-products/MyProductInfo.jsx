import React from "react";

export default function MyProductInfo({ product }){
    return (
        <div className="vendor-product-details">
            <h4>{product.productName}</h4>
            <span className="vendor-product-category-tag">
                <strong>Category: </strong>📁{product.categoryName}
            </span>

            <p className="vendor-product-price">Price: ${product.price?.toFixed(2)}</p>

            <div className="vendor-product-rating">
                {product.averageRating !== null && product.averageRating !== undefined ? (
                    <>
                        <strong>Avg Rating: </strong>
                        <span>⭐ {product.averageRating.toFixed(1)} / 5.0</span>
                    </>
                ) : (
                    <>
                        <strong>Avg Rating: </strong>
                        <span className="no-rating-text">No ratings yet</span>
                    </>
                )}
            </div>

            <span className={`stock-badge ${product.quantity > 0 ? 'in-stock' : 'out-of-stock'}`}>
                Stock: {product.quantity} units
            </span>
        </div>
    );
}