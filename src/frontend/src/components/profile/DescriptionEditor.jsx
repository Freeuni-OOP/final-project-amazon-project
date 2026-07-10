import React from "react";

export default function DescriptionEditor({ value, handleInputChange }){
    return (
        <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
                id="description"
                name="description"
                value={value}
                onChange={handleInputChange}
                rows="4"
            />
        </div>
    );
}