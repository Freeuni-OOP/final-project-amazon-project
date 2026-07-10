import React from "react";

export default function MyProductImage({ product }){

    const hasImages = product.imageUrls && product.imageUrls.length > 0;
    const firstImageUrl = hasImages ? product.imageUrls[0] : "https://via.placeholder.com/150";

    return (
        <img
            src={firstImageUrl}
            alt={product.productName}
            className="vendor-product-img"
        />
    );
}