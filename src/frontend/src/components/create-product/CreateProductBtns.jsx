import React from "react";

export default function CreateProductBtns({ submitting }){
    return (
        <div className="form-actions-row">
            <button type="submit" className="save-changes-btn" disabled={submitting}>
                {submitting ? "Publishing..." : "Publish Listing"}
            </button>
            <button type="button" className="cancel-btn" onClick={() => navigate('/profile', { state: { defaultTab: 'products' } })}>
                Cancel
            </button>
        </div>
    );
}