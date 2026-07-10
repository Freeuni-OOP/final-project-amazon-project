import UploadedFilePreview from "./UploadedFilePreview.jsx";
import React from "react";

export default function UploadedFilePreviewList({ selectedFiles, handleRemoveImage }){
    return (
        <div className="upload-preview-container">
            {selectedFiles.map((file, index) => {
                const previewUrl = URL.createObjectURL(file);
                return (
                    <UploadedFilePreview
                        index={index}
                        previewUrl={previewUrl}
                        handleRemoveImage={handleRemoveImage}
                    />
                );
            })}
        </div>
    );
}