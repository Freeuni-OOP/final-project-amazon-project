import React from 'react'

export default function ItemQuantityField({ item, onUpdateQuantity }){
    return (
        <div className="cart-item-qty-selector">
            <label>Quantity: </label>
            <input
                type="number"
                min="1"
                max="999"
                value={item.quantity}
                onChange={(e) => {
                    const val = parseInt(e.target.value);
                    if (val > 0 && val < 1000) {
                        onUpdateQuantity(item.productId, val);
                    }
                }}
            />
        </div>
    );
}