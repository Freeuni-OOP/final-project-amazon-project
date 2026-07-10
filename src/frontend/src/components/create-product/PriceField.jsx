import React from "react";

export default function PriceField({ formData, handleInputChange }){
    return (
        <div className="form-group">
            <label>Price ($)</label>
            <input className="file-input-field"
                   type="number"
                   min="0.01"
                   step="0.01"
                   name="price"
                   value={formData.price}
                   onChange={handleInputChange} required />
        </div>
    );
}