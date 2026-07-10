import React from 'react'

export default function MyProductBtns({ handleEditProduct, handleDeleteProduct }){
    return (
        <div className="vendor-card-actions">
            <button className="vendor-edit-btn" onClick={() => alert("ToDo: Edit")}>
                Edit
            </button>
            <button className="vendor-delete-btn" onClick={() => alert("ToDo: Delete")}>
                Delete
            </button>
        </div>
    );
}