import React from "react";

export default function QuantityEditor({ value, handleInputChange }){
    return (
        <div className="form-group">
            <label htmlFor="quantity">Available Stock Units</label>
            <input
                type="number"
                id="quantity"
                name="quantity"
                value={value}
                onChange={handleInputChange}
            />
        </div>
    );
}