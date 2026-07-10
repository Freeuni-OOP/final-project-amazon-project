import React from 'react'

export default function CartSummary({ cartItems, subtotal, onCheckout, checkingOut }){
    return (
        <div className="cart-total-summary-card">
            <h4>Order Summary</h4>
            <div className="summary-item-line">
                <span>Subtotal ({cartItems.length} item{cartItems.length !== 1 ? 's' : ''}):</span>
                <span>${subtotal.toFixed(2)}</span>
            </div>
            <div className="summary-item-line">
                <span>Shipping:</span>
                <span className="shipping-promo-tag">FREE</span>
            </div>
            <hr className="summary-divider-line" />
            <div className="summary-item-line total-highlight-line">
                <strong>Estimated Total:</strong>
                <strong>${subtotal.toFixed(2)}</strong>
            </div>
            <button
                type="button"
                className="checkout-action-btn"
                onClick={onCheckout}
                disabled={checkingOut}
            >
                {checkingOut ? "Processing..." : "Proceed to Checkout"}
            </button>
        </div>
    );
}