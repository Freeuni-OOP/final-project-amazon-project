import React from "react";

export default function NameField({ formData, handleInputChange }){
    return (
        <div className="form-group">
            <label>Product Name</label>
            <input className="file-input-field"
                   type="text"
                   name="productName"
                   maxLength={300}
                   value={formData.productName}
                   onChange={handleInputChange} required />
        </div>
    );
}