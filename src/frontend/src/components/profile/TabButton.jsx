import React from 'react';

export default function TabButton({ activeTab, setActiveTab, tab, name }){
    return (
        <button
            onClick={() => setActiveTab(tab)}
            className={`nav-btn ${activeTab === tab ? 'active' : ''}`}
        >{name}</button>
    );
}