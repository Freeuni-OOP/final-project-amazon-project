import React from "react";

export default function UploadedFilePreview({ index, previewUrl, handleRemoveImage }){
    return (
        <div key={index} className="preview-thumbnail">
            <img
                src={previewUrl}
                alt={`Selected Preview ${index + 1}`}
                className="preview-image"
            />
            <button className="remove-preview-btn"
                    type="button"
                    onClick={() => handleRemoveImage(index)}
                    title="Remove image"
            >
                &times;
            </button>
        </div>
    );
}