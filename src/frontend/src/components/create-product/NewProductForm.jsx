import React from 'react';
import ImageEditor from "../edit-product/ImageEditor.jsx";
import CategorySelection from "./CategorySelection.jsx";
import NameField from "./NameField.jsx";
import PriceField from "./PriceField.jsx";
import QuantityField from "./QuantityField.jsx";
import DescriptionField from "./DescriptionField.jsx";
import CreateProductBtns from "./CreateProductBtns.jsx";

export default function NewProductForm({ formData, selectedImages, setSelectedImages, handleInputChange, handleFormSubmit, submitting }){
    return (
        <form onSubmit={handleFormSubmit} className="edit-product-form">
            <NameField formData={formData} handleInputChange={handleInputChange} />
            <CategorySelection formData={formData} handleInputChange={handleInputChange} />
            <PriceField formData={formData} handleInputChange={handleInputChange} />
            <QuantityField formData={formData} handleInputChange={handleInputChange} />
            <DescriptionField formData={formData} handleInputChange={handleInputChange} />
            <ImageEditor selectedFiles={selectedImages} onFilesChange={setSelectedImages} />
            <CreateProductBtns submitting={submitting} />
        </form>
    );
}