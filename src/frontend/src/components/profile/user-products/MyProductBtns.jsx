import React from 'react'
import { useNavigate } from 'react-router-dom';

export default function MyProductBtns({ product, handleDeleteProduct }){
    const navigate = useNavigate();

    return (
        <div className="vendor-card-actions">
            <button
                className="vendor-edit-btn"
                onClick={() => navigate(`/edit-product/${product.productId}`)}
            >
                Edit
            </button>
            <button className="vendor-delete-btn" onClick={() => handleDeleteProduct(product.productId)}>
                Delete
            </button>
        </div>
    );
}