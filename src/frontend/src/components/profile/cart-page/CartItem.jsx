import React from 'react'
import ItemInfo from "./ItemInfo.jsx";
import ItemQuantityField from "./ItemQuantityField.jsx";
import ItemTotal from "./ItemTotal.jsx";
import ItemDeleteBtn from "./ItemDeleteBtn.jsx";

export default function CartItem({ item, onUpdateQuantity, onRemoveItem }){
    const price = item.price || 0;
    const title = item.productName || `Product #${item.productId}`;

   const getImageUrl = (url) => {
           if (!url) return 'http://localhost:8080/photos/No-image-placeholder.png';
           if (url.startsWith('http')) return url;
           return `http://localhost:8080${url}`;
       };


       const imageUrl = item.imageUrls && item.imageUrls.length > 0
           ? getImageUrl(item.imageUrls[0])
           : "http://localhost:8080/photos/No-image-placeholder.png";
    return (
        <div key={item.id} className="cart-item-row-card">
            <div className="cart-item-image-container">
                <img
                    src={imageUrl}
                    alt={title}
                    className="cart-item-thumbnail"
                />
            </div>
            <ItemInfo title={title} price={price} />
            <ItemQuantityField item={item} onUpdateQuantity={onUpdateQuantity} />
            <div className="cart-item-pricing-actions">
                <ItemTotal price={price} quantity={item.quantity} />
                <ItemDeleteBtn item={item} onRemoveItem={onRemoveItem} />
            </div>
        </div>
    );
}
