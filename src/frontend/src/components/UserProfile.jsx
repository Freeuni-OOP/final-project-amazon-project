import React, { useState, useEffect } from 'react';
import AccountOverview from './profile/AccountOverview.jsx';
import TabButton from "./profile/TabButton.jsx";
import './UserProfile.css';

export default function UserProfile() {
    const [activeTab, setActiveTab] = useState('profile');
    const [loading, setLoading] = useState(false);
    const [userInfo, setUserInfo] = useState({
        username: 'Loading...', email: 'Loading...', balance: 0.00, gender: '', birthDate: ''
    });

    const currentUserId = 1;

    useEffect(() => {
        if (activeTab === 'profile') {
            setLoading(true);
            fetch(`http://localhost:8080/users/${currentUserId}`)
                .then(res => res.json())
                .then(data => { setUserInfo(data); setLoading(false); })
                .catch(err => { console.error(err); setLoading(false); });
        }
    }, [activeTab]);

    const renderTabContent = () => {
        switch (activeTab) {
            case 'profile':
                return <AccountOverview userInfo={userInfo} />;
            case 'comments':
                return <div><h3>My Written Comments</h3></div>; // ToDo by me
            case 'cart':
                return <div><h3>My Shopping Cart</h3></div>; // ToDo by me
            case 'products':
                return <div><h3>My Selling Products</h3></div>; // ToDo by me
            case 'transactions':
                return <div><h3>Order History</h3></div>; // ToDo by Mariami
            default:
                return <div>Select an option from the menu.</div>;
        }
    };

    return (
        <div className="dashboard-layout-container">
            <div className="dashboard-sidebar">
                <h2>My Account</h2>
                <TabButton activeTab={activeTab} setActiveTab={setActiveTab} tab={'profile'} name={'Personal Info'}/>
                <TabButton activeTab={activeTab} setActiveTab={setActiveTab} tab={'cart'} name={'My Cart'}/>
                <TabButton activeTab={activeTab} setActiveTab={setActiveTab} tab={'products'} name={'My Products'}/>
                <TabButton activeTab={activeTab} setActiveTab={setActiveTab} tab={'comments'} name={'My Comments'}/>
                <TabButton activeTab={activeTab} setActiveTab={setActiveTab} tab={'transactions'} name={'Order History'}/>
            </div>

            <div className="dashboard-main-content">
                {renderTabContent()}
            </div>
        </div>
    );
}