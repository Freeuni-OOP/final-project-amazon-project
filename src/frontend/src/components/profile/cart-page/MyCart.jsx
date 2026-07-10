import React from 'react';
import CartItemList from "./CartItemList.jsx";
import CartSummary from "./CartSummary.jsx";

export default function MyCart({ cartItems, loading, onUpdateQuantity, onRemoveItem }) {

    const subtotal = cartItems.reduce((acc, item) => {
        const itemPrice = item.price || 0;
        return acc + (itemPrice * item.quantity);
    }, 0);

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
                    <CartItemList cartItems={cartItems} onRemoveItem={onRemoveItem} onUpdateQuantity={onUpdateQuantity} />
                    <CartSummary cartItems={cartItems} subtotal={subtotal} />
                </div>
            )}
        </div>
    );
}