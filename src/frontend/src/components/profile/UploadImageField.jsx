import React from "react";

export default function UploadImageField({ handleFileChange }){
    return (
        <div className="upload-input-wrapper">
            <input className="file-input-field"
                   type="file"
                   id="images"
                   multiple
                   accept="image/*"
                   onChange={handleFileChange}
            />
        </div>
    );
}