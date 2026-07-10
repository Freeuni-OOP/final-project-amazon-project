import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CartItemList from "./CartItemList.jsx";
import CartSummary from "./CartSummary.jsx";

export default function MyCart({ cartItems, loading, onUpdateQuantity, onRemoveItem }) {
    const navigate = useNavigate();
    const [checkingOut, setCheckingOut] = useState(false);

    const storedUser = localStorage.getItem('user');
    const userObj = storedUser ? JSON.parse(storedUser) : null;
    const currentUserId = userObj?.id || localStorage.getItem('userId');

    const subtotal = cartItems.reduce((acc, item) => {
        const itemPrice = item.price || 0;
        return acc + (itemPrice * item.quantity);
    }, 0);

    const handleCheckout = async () => {
        const confirmCheckout = window.confirm("Are you sure you want to finalize your order and checkout?");
        if (!confirmCheckout) return;

        setCheckingOut(true);

        try {
            const response = await fetch(`http://localhost:8080/orders/create/${currentUserId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });

            if (response.ok) {
                const newOrderData = await response.json();

                alert("🎉 Order created successfully!");

                if (newOrderData && newOrderData.orderId) {
                    navigate(`/order-details/${newOrderData.orderId}`);
                } else {
                    // Fallback to history tab if backend failed to supply the ID field
                    navigate('/profile', { state: { defaultTab: 'transactions' } });
                }
            } else {
                alert("Checkout failed. Please check product stock metrics or your balance.");
            }
        } catch (error) {
            console.error("Error during checkout sequence execution:", error);
            alert("Network connection error encountered during checkout.");
        } finally {
            setCheckingOut(false);
        }
    };

    return (
        <div className="profile-cart-tab">
            <h3>My Shopping Cart</h3>

            {loading && <p className="status-msg">Loading your shopping cart...</p>}

            {!loading && cartItems.length === 0 && (
                <div className="empty-cart-container">
                    <p>Your shopping cart is completely empty.</p>
                </div>
            )}

            {!loading && cartItems.length > 0 && (
                <div className="cart-layout-split">
                    <CartItemList
                        cartItems={cartItems}
                        onRemoveItem={onRemoveItem}
                        onUpdateQuantity={onUpdateQuantity}
                    />
                    <CartSummary
                        cartItems={cartItems}
                        subtotal={subtotal}
                        onCheckout={handleCheckout}
                        checkingOut={checkingOut}
                    />
                </div>
            )}
        </div>
    );
}