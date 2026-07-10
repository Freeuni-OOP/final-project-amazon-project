import React from "react";

export default function NameEditor({ value, handleInputChange }){
    return (
        <div className="form-group">
            <label htmlFor="productName">Product Name</label>
            <input
                type="text"
                id="productName"
                name="productName"
                value={value}
                onChange={handleInputChange}
            />
        </div>
    );
}