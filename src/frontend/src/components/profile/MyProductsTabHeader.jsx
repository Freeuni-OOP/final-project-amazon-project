import React from 'react'

export default function MyProductsTabHeader(){
    return (
        <div className="vendor-dashboard-header">
            <div>
                <h3>My Listed Products</h3>
                <p className="subtext">Manage your marketplace inventory and stock metrics.</p>
            </div>
            <button className="add-product-btn" onClick={() => alert("ToDo: Open Add Product Modal")}>
                + Add New Product
            </button>
        </div>
    );
}