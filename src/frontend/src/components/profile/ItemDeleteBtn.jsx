import React from 'react'

export default function ItemDeleteBtn({item, onRemoveItem}){
    return (
        <button
            className="cart-delete-item-btn"
            onClick={() => onRemoveItem(item.id)}
        >
            Delete
        </button>
    );
}