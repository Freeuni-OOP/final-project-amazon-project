import React from "react";

export default function MyProductImage({ product }){

    const getImageUrl = (url) => {
        if (!url) return 'http://localhost:8080/photos/No-image-placeholder.png';
        if (url.startsWith('http')) return url;
        return `http://localhost:8080${url}`;
    };

    return (
        <img
            src={getImageUrl(product.imageUrls?.[0])}
            alt={product.productName}
            className="vendor-product-img"
        />
    );
}