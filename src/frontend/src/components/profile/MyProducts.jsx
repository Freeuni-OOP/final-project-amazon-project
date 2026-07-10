import React, { useEffect, useState } from 'react';
import '../UserProfile.css';
import MyProductsTabHeader from "./MyProductsTabHeader.jsx";
import MyProduct from "./MyProduct.jsx";

export default function MyProducts() {
    const sellerId = 1;

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchSellerProducts = async () => {
            try {
                const response = await fetch(`http://localhost:8080/products/seller/${sellerId}`);
                const data = await response.json();
                setProducts(data);
            } catch (error) {
                console.error("Error fetching user's products:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchSellerProducts();
    }, [sellerId]);

    const handleEditProduct = (productId) => {
        alert(`ToDo: Open edit modal/page for product ID: ${productId}`);
    };

    const handleDeleteProduct = (productId) => {
        if (window.confirm("Are you sure you want to delete this listing from the market?")) {
            alert(`ToDo: Delete API call for product ID: ${productId}`);
        }
    };

    return (
        <div className="profile-products-tab">
            <MyProductsTabHeader/>

            {loading && <p className="status-msg">Loading your inventory records...</p>}

            {!loading && products.length === 0 && (
                <div className="empty-products-container">
                    <p>You aren't selling any products yet. Click the button above to create your first listing!</p>
                </div>
            )}

            {!loading && products.length > 0 && (
                <div className="vendor-products-grid">
                    {products.map((product) => (
                        <MyProduct product={product} handleEditProduct={handleEditProduct} handleDeleteProduct={handleDeleteProduct}/>
                    ))}
                </div>
            )}
        </div>
    );
}