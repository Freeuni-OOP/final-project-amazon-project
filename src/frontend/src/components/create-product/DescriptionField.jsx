import React from "react";

export default function ({ formData, handleInputChange }){
    return (
        <div className="form-group">
            <label>Product Description</label>
            <textarea className="file-input-field"
                      name="description"
                      rows="4"
                      maxLength={800}
                      value={formData.description}
                      onChange={handleInputChange} required />
        </div>
    );
}