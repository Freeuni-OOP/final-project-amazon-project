import React from 'react';
import { useNavigate} from "react-router-dom";

export default function MyProductsTabHeader(){
    const navigate = useNavigate();

    return (
        <div className="vendor-dashboard-header">
            <div>
                <h3>My Listed Products</h3>
                <p className="subtext">Manage your marketplace inventory and stock metrics.</p>
            </div>
            <button className="add-product-btn" onClick={ () => navigate(`/create-product`) }>
                + Add New Product
            </button>
        </div>
    );
}