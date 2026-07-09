import React, { useState, useEffect } from 'react';
import AccountOverview from './profile/AccountOverview.jsx';
import TabButton from "./profile/TabButton.jsx";
import './UserProfile.css';
import MyCart from "./profile/MyCart.jsx";

export default function UserProfile() {
    const [activeTab, setActiveTab] = useState('profile');
    const [loading, setLoading] = useState(false);
    const [userInfo, setUserInfo] = useState({
        username: 'Loading...', email: 'Loading...', balance: 0.00, gender: '', birthDate: ''
    });
    const [cartItems, setCartItems] = useState([]);

    const currentUserId = 1;

    useEffect(() => {
        if (activeTab === 'profile') {
            setLoading(true);
            fetch(`http://localhost:8080/users/${currentUserId}`)
                .then(res => res.json())
                .then(data => { setUserInfo(data); setLoading(false); })
                .catch(err => { console.error(err); setLoading(false); });
        }

        if (activeTab === 'cart') {
            setLoading(true);
            fetch(`http://localhost:8080/cartItem/user/${currentUserId}`)
                .then(res => res.json())
                .then(data => {
                    setCartItems(data);
                    setLoading(false);
                })
                .catch(err => {
                    console.error("Error fetching cart items:", err);
                    setLoading(false);
                });
        }

    }, [activeTab]);


    const handleUpdateQuantity = (productId, newQty, onFailure) => {
        if (newQty === '') {
            setCartItems(prev => prev.map(item => item.productId === productId ? { ...item, quantity: '' } : item));
            return;
        }

        const requestBody = {
            userId: currentUserId,
            productId: productId,
            quantity: newQty
        };

        fetch('http://localhost:8080/cartItem/update', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (res.ok) {
                    setCartItems(prev => prev.map(item => item.productId === productId ? { ...item, quantity: newQty } : item));
                } else {
                    if (onFailure) {
                        onFailure("Not enough stock available");
                    }
                }
            })
            .catch(err => {
                console.error("Backend update failed:", err);
            });
    };

    const handleRemoveItem = (cartItemId) => {
        setCartItems(prev => prev.filter(item => item.id !== cartItemId));

        fetch(`http://localhost:8080/cartItem/${cartItemId}`, {
            method: 'DELETE'
        })
            .then(res => res.text())
            .then(msg => console.log(msg))
            .catch(err => console.error("Error removing cart item:", err));
    };


    const renderTabContent = () => {
        switch (activeTab) {
            case 'profile':
                return <AccountOverview userInfo={userInfo} />;
            case 'comments':
                return <div><h3>My Written Comments</h3></div>;     // ToDo: by me
            case 'cart':
                return <MyCart
                    cartItems={cartItems}
                    loading={loading}
                    onUpdateQuantity={handleUpdateQuantity}
                    onRemoveItem={handleRemoveItem}
                />;
            case 'products':
                return <div><h3>My Selling Products</h3></div>;     // ToDo: by me
            case 'transactions':
                return <div><h3>Order History</h3></div>;           // ToDo: by Mariami
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