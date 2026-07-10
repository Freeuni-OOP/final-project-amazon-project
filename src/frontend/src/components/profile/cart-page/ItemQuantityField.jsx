import React, { useState, useEffect } from 'react';

export default function ItemQuantityField({ item, onUpdateQuantity }){
    const [localQty, setLocalQty] = useState(item.quantity);
    const [error, setError] = useState('');

    useEffect(() => {
        setLocalQty(item.quantity);
    }, [item.quantity]);

    const handleClearError = () => {
        if (error) setError('');
    };

    return (
        <div className="cart-item-qty-container">
            <div className="cart-item-qty-selector">
                <label>Quantity: </label>
                <input
                    type="number"
                    min="1"
                    max="999"
                    value={item.quantity}
                    onWheel={(e) => e.target.blur()}
                    onInput={handleClearError}
                    onFocus={handleClearError}
                    onChange={(e) => {
                        if (e.target.value === '') {
                            setLocalQty('');
                            setError('');
                            onUpdateQuantity(item.productId, '', () => {});
                            return;
                        }

                        const val = parseInt(e.target.value);

                        if (val > 0 && val < 1000) {
                            setLocalQty(val);
                            setError('');
                            onUpdateQuantity(item.productId, val, (errorMessage) => {
                                setLocalQty(item.quantity);
                                setError(errorMessage);
                            });
                        }
                    }}
                />
            </div>
            {error && <span className="qty-error-label">{error}</span>}
        </div>
    );
}