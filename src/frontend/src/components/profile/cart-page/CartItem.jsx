import React from 'react'
import ItemInfo from "./ItemInfo.jsx";
import ItemQuantityField from "./ItemQuantityField.jsx";
import ItemTotal from "./ItemTotal.jsx";
import ItemDeleteBtn from "./ItemDeleteBtn.jsx";

export default function CartItem({ item, onUpdateQuantity, onRemoveItem }){
    const price = item.price || 0;
    const title = item.productName || `Product #${item.productId}`;

    return (
        <div key={item.id} className="cart-item-row-card">
            <ItemInfo title={title} price={price} />
            <ItemQuantityField item={item} onUpdateQuantity={onUpdateQuantity} />
            <div className="cart-item-pricing-actions">
                <ItemTotal price={price} quantity={item.quantity} />
                <ItemDeleteBtn item={item} onRemoveItem={onRemoveItem} />
            </div>
        </div>
    );
}