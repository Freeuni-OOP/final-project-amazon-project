import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import MainPage from '../MainPage.jsx';
import NameEditor from "./NameEditor.jsx";
import PriceEditor from "./PriceEditor.jsx";
import QuantityEditor from "./QuantityEditor.jsx";
import DescriptionEditor from "./DescriptionEditor.jsx";
import EditFormButtons from "./EditFormButtons.jsx";
import './EditProductPage.css';
import ImageEditor from "./ImageEditor.jsx";

export default function EditProductPage() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        productName: '',
        price: '',
        quantity: '',
        description: ''
    });
    const [selectedImages, setSelectedImages] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchProductToEdit = async () => {
            try {
                const response = await fetch(`http://localhost:8080/products/${id}`);
                const data = await response.json();

                setFormData({
                    productName: data.productName || '',
                    price: data.price || '',
                    quantity: data.quantity || '',
                    description: data.description || ''
                });
            } catch (error) {
                console.error("Error fetching product data:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchProductToEdit();
    }, [id]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        const updatePromises = [];

        updatePromises.push(
            fetch(`http://localhost:8080/products/${id}/details`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    productName: formData.productName,
                    description: formData.description
                })
            })
        );

        updatePromises.push(
            fetch(`http://localhost:8080/products/${id}/price`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ price: parseFloat(formData.price) })
            })
        );

        updatePromises.push(
            fetch(`http://localhost:8080/products/${id}/quantity`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ quantity: parseInt(formData.quantity, 10) })
            })
        );

        if (selectedImages && selectedImages.length > 0) {
            const imageData = new FormData();

            for (let i = 0; i < selectedImages.length; i++) {
                imageData.append('images', selectedImages[i]);
            }

            updatePromises.push(
                fetch(`http://localhost:8080/products/${id}/image`, {
                    method: 'PATCH',
                    body: imageData
                })
            );
        }

        try {
            const responses = await Promise.all(updatePromises);
            const allSuccessful = responses.every(res => res.ok);

            if (allSuccessful) {
                alert("All product options synced up successfully!");
                navigate('/profile', { state: { defaultTab: 'products' } });
            } else {
                alert("Some fields failed validation parameters during sync.");
            }
        } catch (error) {
            console.error("Failed to update product variants:", error);
        }
    };

    return (
        <MainPage>
            <div className="edit-product-container">
                <h3>📝 Edit Marketplace Listing</h3>
                <p className="product-reference-id">Product Target ID Reference: <strong>#{id}</strong></p>

                {loading ? (
                    <p className="loading-msg">Fetching listing parameters from server...</p>
                ) : (
                    <form onSubmit={handleFormSubmit} className="edit-product-form">
                        <NameEditor value={formData.productName} handleInputChange={handleInputChange} />
                        <PriceEditor value={formData.price} handleInputChange={handleInputChange} />
                        <QuantityEditor value={formData.quantity} handleInputChange={handleInputChange} />
                        <DescriptionEditor value={formData.description} handleInputChange={handleInputChange} />
                        <ImageEditor selectedFiles={selectedImages} onFilesChange={setSelectedImages} />
                        <EditFormButtons />
                    </form>
                )}
            </div>
        </MainPage>
    );
}