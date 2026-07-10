import React from "react";

export default function QuantityField({ formData, handleInputChange }){
    return (
        <div className="form-group">
            <label>Starting Quantity Units</label>
            <input className="file-input-field"
                   type="number"
                   min="1"
                   step="1"
                   name="quantity"
                   value={formData.quantity}
                   onChange={handleInputChange} required />
        </div>
    );
}