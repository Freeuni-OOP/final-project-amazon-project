import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import MainPage from '../MainPage.jsx';
import '../edit-product/EditProductPage.css';
import NewProductForm from "./NewProductForm.jsx";

export default function AddProductPage() {
    const navigate = useNavigate();
    const currentUserId = 1; // ToDo:  remove the hardcoded value.

    const [formData, setFormData] = useState({
        productName: '',
        price: '',
        quantity: '',
        description: '',
        categoryName: 'other'
    });
    const [selectedImages, setSelectedImages] = useState([]);
    const [submitting, setSubmitting] = useState(false);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        setSubmitting(true);

        const dataPayload = new FormData();

        const productMetadata = {
            sellerId: currentUserId,
            productName: formData.productName,
            description: formData.description,
            price: parseFloat(formData.price),
            quantity: parseInt(formData.quantity, 10),
            categoryName: formData.categoryName,
            categoryId: null
        };

        dataPayload.append('product', new Blob([JSON.stringify(productMetadata)], {
            type: 'application/json'
        }));

        for (let i = 0; i < selectedImages.length; i++) {
            dataPayload.append('images', selectedImages[i]);
        }

        try {
            const response = await fetch('http://localhost:8080/products', {
                method: 'POST',
                body: dataPayload
            });

            if (response.ok) {
                alert("Product added successfully!");
                navigate('/profile', { state: { defaultTab: 'products' } });
            } else {
                alert("Server rejected product parameters.");
            }
        } catch (error) {
            console.error("Failed to add a new product:", error);
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <MainPage>
            <div className="edit-product-container">
                <h3>✨ Add New Product to the Market</h3>
                <p className="product-reference-id">Create a completely new product row inside inventory.</p>

                <NewProductForm
                    formData={formData}
                    selectedImages={selectedImages}
                    setSelectedImages={setSelectedImages}
                    handleInputChange={handleInputChange}
                    handleFormSubmit={handleFormSubmit}
                    submitting={submitting}
                />
            </div>
        </MainPage>
    );
}