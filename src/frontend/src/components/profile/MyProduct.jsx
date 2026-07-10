import React from "react";
import MyProductBtns from "./MyProductBtns.jsx";
import MyProductInfo from "./MyProductInfo.jsx";
import MyProductImage from "./MyProductImage.jsx";

export default function MyProduct({ product, handleEditProduct, handleDeleteProduct }){
    return (
        <div key={product.productId} className="vendor-product-card">
            <MyProductImage product={product}/>
            <MyProductInfo product={product}/>
            <MyProductBtns handleEditProduct={handleEditProduct} handleDeleteProduct={handleDeleteProduct}/>
        </div>
    );
}