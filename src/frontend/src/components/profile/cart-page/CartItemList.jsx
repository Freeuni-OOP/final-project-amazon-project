import React from "react"
import CartItem from "./CartItem.jsx";

export default function CartItemList({ cartItems, onUpdateQuantity, onRemoveItem }){
    return (
        <div className="cart-items-column">
            <div className="cart-items-scroll-container">
                {cartItems.map((item) =>
                    <CartItem
                        key={item.productId}
                        item={item}
                        onUpdateQuantity={onUpdateQuantity}
                        onRemoveItem={onRemoveItem}
                    />
                )}
            </div>
        </div>
    );
}
