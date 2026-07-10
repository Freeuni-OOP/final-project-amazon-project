import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import AccountOverview from './user-info/AccountOverview.jsx';
import TabButton from "./TabButton.jsx";
import './UserProfile.css';
import MyCart from "./cart-page/MyCart.jsx";
import MyProducts from "./user-products/MyProducts.jsx";
import OrderHistory from "./order-history/OrderHistory.jsx";

export default function UserProfile() {
    const location = useLocation();
    const [activeTab, setActiveTab] = useState(location.state?.defaultTab || 'profile');
    const [loading, setLoading] = useState(false);
    const [userInfo, setUserInfo] = useState({
        username: 'Loading...', email: 'Loading...', balance: 0.00, gender: '', birthDate: ''
    });
    const [cartItems, setCartItems] = useState([]);

    const storedUser = localStorage.getItem('user');
    const userObj = storedUser ? JSON.parse(storedUser) : null;
    const currentUserId = userObj?.id || localStorage.getItem('userId');

    useEffect(() => {
        if (activeTab === 'profile') {
            // eslint-disable-next-line react-hooks/set-state-in-effect
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
                return <div><h3>My Written Comments</h3></div>;
            case 'cart':
                return <MyCart
                    cartItems={cartItems}
                    loading={loading}
                    onUpdateQuantity={handleUpdateQuantity}
                    onRemoveItem={handleRemoveItem}
                />;
            case 'products':
                return <MyProducts/>
           case 'transactions':
               return <OrderHistory />;
            default:
                return <div>Select an option from the menu.</div>;
        }
    };

    return (
        <div className="dashboard-layout-container">
            <div className="dashboard-sidebar">
                <h2 className="my-account-header">My Account</h2>
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