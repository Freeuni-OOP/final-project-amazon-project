import React from "react"
import CartItem from "./CartItem.jsx";

export default function CartItemList({ cartItems, onUpdateQuantity, onRemoveItem }){
    return (
        <div className="cart-items-column">
            {cartItems.map((item) =>
                <CartItem item={item} onUpdateQuantity={onUpdateQuantity} onRemoveItem={onRemoveItem} />
            )}
        </div>
    );
}
