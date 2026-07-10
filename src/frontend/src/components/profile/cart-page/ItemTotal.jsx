import React from 'react'

export default function ItemTotal({ price, quantity }){
    return (
        <p className="cart-row-total">
            Total: <strong>${(price * quantity).toFixed(2)}</strong>
        </p>
    );
}