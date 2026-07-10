import React from "react";

export default function CategorySelection({ formData, handleInputChange}){
    return (
        <div className="form-group">
            <label>Category</label>
            <select className="file-input-field" name="categoryName" value={formData.categoryName} onChange={handleInputChange}>
                <option value="Beauty">Beauty</option>
                <option value="Clothing, Shoes & Jewelry">Clothing, Shoes & Jewelry</option>
                <option value="Electronics & Gadgets">Electronics & Gadgets</option>
                <option value="Entertainment">Entertainment</option>
                <option value="Furniture">Furniture</option>
                <option value="Other">Other</option>
                <option value="Sports & Outdoors">Sports & Outdoors</option>
            </select>
        </div>
    );
}