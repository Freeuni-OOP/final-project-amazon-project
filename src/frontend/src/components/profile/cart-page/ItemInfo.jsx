import React from 'react'

export default function ItemInfo({ title, price }){
    return (
        <div className="cart-item-info">
            <h4>{title}</h4>
            <p className="cart-item-unit-price">
                Price: ${Number(price).toFixed(2)}
            </p>
        </div>
    );
}