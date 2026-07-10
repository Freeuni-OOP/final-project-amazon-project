import React from "react";
import {useNavigate} from 'react-router-dom';

export default function EditFormButtons(){
    const navigate = useNavigate();

    return (
        <div className="form-actions-row">
            <button type="submit" className="save-changes-btn">
                Save Changes
            </button>
            <button type="button" className="cancel-btn" onClick={() => navigate(`/profile`, { state: { defaultTab: 'products' } })}>
                Cancel
            </button>
        </div>
    );
}