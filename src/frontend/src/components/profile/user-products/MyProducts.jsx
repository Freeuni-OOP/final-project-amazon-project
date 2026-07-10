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

    const handleDeleteProduct = async (productId) => {
        const confirmDelete = window.confirm("Are you sure you want to delete this listing permanently?");
        if (!confirmDelete) return;

        try {
            const response = await fetch(`http://localhost:8080/products/${productId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                setProducts(prevProducts => prevProducts.filter(p => p.productId !== productId));
                alert("Listing deleted successfully.");
            } else {
                alert("Failed to delete product parameters from server.");
            }
        } catch (error) {
            console.error("Error during deletion request:", error);
            alert("Network connection failure during deletion.");
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
                <div className="my-products-list">
                    {products.map((product) => (
                        <MyProduct product={product} handleDeleteProduct={handleDeleteProduct}/>
                    ))}
                </div>
            )}
        </div>
    );
}