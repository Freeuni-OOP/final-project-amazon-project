import React from "react";

export default function PriceEditor({ value, handleInputChange }){
    return (
        <div className="form-group">
            <label htmlFor="price">Price ($)</label>
            <input
                type="number"
                step="0.01"
                id="price"
                name="price"
                value={value}
                onChange={handleInputChange}
            />
        </div>
    );
}