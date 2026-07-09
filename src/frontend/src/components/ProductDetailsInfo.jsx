import '../App.css';
import {useState} from "react";

function ProductDetailsInfo({product}) {
    const [currentQuantity, setCurrentQuantity] = useState(product.quantity);

    const storedUser = localStorage.getItem('user');
    const userObj = storedUser ? JSON.parse(storedUser) : null;
    const currentUserId = userObj?.id || localStorage.getItem('userId');

    const handleAddToCart = async () => {
        if (!currentUserId) {
            alert("Please Sign In to add products to your cart! 🛒");
            return;
        }

        if (currentQuantity <= 0) return;

        try {
            const response = await fetch(`http://localhost:8080/cart/add?userId=${currentUserId}&productId=${product.productId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });

            if (!response.ok) throw new Error("Product was not added to Cart");

            setCurrentQuantity(prev => prev - 1);
            alert("Product successfully added to cart! 🛒");

        } catch (error) {
            console.error("Error adding to cart:", error);
            alert("Error adding product to cart.");
        }
    };

    return (
        <div className="product-info-side" style={{ flex: '1.5' }}>
            <h1 className="product-title-text" style={{ fontSize: '28px', fontWeight: '700', color: '#0F1111', marginBottom: '10px', lineHeight: '1.2' }}>
                {product.productName}
            </h1>
            <p className="product-seller-text" style={{ color: '#565959', fontSize: '15px', margin: '0 0 15px 0' }}>
                Brand/Seller: <span style={{ color: '#007185', fontWeight: '700' }}>{product.sellerName || "Unknown"}</span>
            </p>
            <hr style={{ border: '0', borderTop: '1px solid #e7e7e7', margin: '15px 0' }} />
            <div className="price-block" style={{ marginTop: '20px', marginBottom: '15px' }}>
                <span className="price-label" style={{ fontSize: '18px', color: '#555' }}>Price: </span>
                <span className="price-value" style={{ fontSize: '22px', fontWeight: 'bold', color: '#0F1111', marginLeft: '5px' }}>${product.price}</span>
            </div>
            <p style={{ fontSize: '15px', fontWeight: '600', color: currentQuantity > 0 ? '#007600' : '#B12704', margin: '10px 0 20px 0' }}>
                {currentQuantity > 0 ? `✓ In Stock (Only ${currentQuantity} left)` : "✗ Out of Stock"}
            </p>
            <div className="description-block" style={{ marginTop: '20px' }}>
                <h3 className="description-title">About this item</h3>
                <p className="description-text" style={{ lineHeight: '1.5' }}>
                    {product.description || "No description available for this product."}
                </p>
            </div>
            <button
                disabled={currentQuantity <= 0}
                onClick={handleAddToCart}
                className="amazon-primary-button"
                style={{
                    width: '100%',
                    maxWidth: '250px',
                    padding: '13px 25px',
                    fontSize: '16px',
                    fontWeight: 'bold',
                    color: '#fff',
                    backgroundColor: '#1E2D42',
                    border: 'none',
                    borderRadius: '100px',
                    cursor: currentQuantity > 0 ? 'pointer' : 'not-allowed',
                    boxShadow: '0 2px 5px rgba(0,0,0,0.1)',
                    transition: 'all 0.2s ease',
                    marginTop: '20px',
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