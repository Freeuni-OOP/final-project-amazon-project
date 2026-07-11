import '../App.css';
import {useState} from "react";

function ProductDetailsInfo({product}) {
    const [currentQuantity, setCurrentQuantity] = useState(product.quantity);

    const storedUser = localStorage.getItem('user');
    const userObj = storedUser ? JSON.parse(storedUser) : null;
    const currentUserId = userObj?.id || localStorage.getItem('userId');

    const handleAddToCart = async () => {
        if (!currentUserId || currentQuantity <= 0) {
            if (!currentUserId) alert("Please Sign In to add products to your cart! 🛒");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/cartItem/add`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ userId: currentUserId, productId: product.productId, quantity: 1 })
            });

            if (!response.ok) throw new Error();

            setCurrentQuantity(prev => prev - 1);

            alert("Product successfully added to cart! 🛒");
        }
        catch {
            alert("Error adding product to cart.");
        }
    };

    return (
        <div className="product-info-side">
            <h1 className="product-title-text">{product.productName}</h1>

            <p className="product-seller-text">
                Brand/Seller:
                <span className="seller-name">{product.sellerName || "Unknown"}</span>
            </p>

            <hr/>

            <div className="price-block">
                <span className="price-label">Price: </span>
                <span className="price-value">${product.price}</span>
            </div>

            <p className="current-quantity" style={{color: currentQuantity > 0 ? '#007600' : '#B12704'}}>
                {currentQuantity > 0 ? `✓ In Stock (Only ${currentQuantity} left)` : "✗ Out of Stock"}
            </p>

            <div className="description-block">
                <h3 className="description-title">About this item</h3>
                <p className="description-text">
                    {product.description || "No description available for this product."}
                </p>
            </div>

            <button
                disabled={currentQuantity <= 0}
                onClick={handleAddToCart}
                className="amazon-primary-button"
                style={{
                    cursor: currentQuantity > 0 ? 'pointer' : 'not-allowed',
                    opacity: currentQuantity > 0 ? 1 : 0.5
                }}
                onMouseOver={(e) => { if(currentQuantity > 0) e.target.style.backgroundColor = '#131A26'; }}
                onMouseOut={(e) => { if(currentQuantity > 0) e.target.style.backgroundColor = '#1E2D42'; }}
                onMouseDown={(e) => { if(currentQuantity > 0) e.target.style.transform = 'scale(0.98)'; }}
                onMouseUp={(e) => { if(currentQuantity > 0) e.target.style.transform = 'scale(1)'; }}
            >
                Add to Cart
            </button>


        </div>
    );
}

export default ProductDetailsInfo;