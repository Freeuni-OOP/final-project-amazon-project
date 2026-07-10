import React from 'react';
import UploadedFilePreviewList from "./UploadedFilePreviewList.jsx";
import UploadImageField from "./UploadImageField.jsx";

export default function ImageEditor({ selectedFiles, onFilesChange }) {

    const handleFileChange = (e) => {
        const newBatch = Array.from(e.target.files);

        if (selectedFiles.length + newBatch.length > 5) {
            e.target.value = "";
            return;
        }

        onFilesChange([...selectedFiles, ...newBatch]);
        e.target.value = "";
    };

    const handleRemoveImage = (indexToRemove) => {
        const filteredFiles = selectedFiles.filter((_, index) => index !== indexToRemove);
        onFilesChange(filteredFiles);
    };

    return (
        <div className="form-group photo-upload-section">
            <label htmlFor="images">Replace Product Photos (Max 5)</label>

            <UploadImageField handleFileChange={handleFileChange}/>

            {selectedFiles.length > 0 && (
                <UploadedFilePreviewList
                    selectedFiles={selectedFiles}
                    handleRemoveImage={handleRemoveImage}
                />
            )}

            <small className="form-text text-muted">
                Selecting new files will replace all existing images for this product.
            </small>
        </div>
    );
}