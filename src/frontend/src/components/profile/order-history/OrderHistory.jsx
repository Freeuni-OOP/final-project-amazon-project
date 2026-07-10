import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TabButton from "../TabButton.jsx";

export default function OrderHistory() {
    const [activeTab, setActiveTab] = useState('my-orders');
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const storedUser = localStorage.getItem('user');
    const userObj = storedUser ? JSON.parse(storedUser) : null;
    const currentUserId = userObj?.id || localStorage.getItem('userId');

    useEffect(() => {
        const fetchOrders = async () => {
            setLoading(true);

            try {
               let url = `http://localhost:8080/orders/user/${currentUserId}`;

               if (activeTab === 'sold-items') {
                   url = `http://localhost:8080/orders/sold/${currentUserId}`;
               }


                const response = await fetch(url);
                if (!response.ok) throw new Error(`Server responded with ${response.status}`);

                const data = await response.json();
                setOrders(Array.isArray(data) ? data : []);
            } catch (error) {
                console.error("Error fetching orders:", error);
                setOrders([]);
            } finally {
                setLoading(false);
            }
        };

        fetchOrders();
    }, [activeTab, currentUserId]);

    const formatDate = (dateString) => {
        if (!dateString) return "";
        return dateString.replace('T', ' ').substring(0, 16);
    };

    return (
        <div className="order-history-container">
            <div className="order-history-tabs">
                <TabButton activeTab={activeTab} setActiveTab={setActiveTab} tab="my-orders" name="My Orders" />
                <TabButton activeTab={activeTab} setActiveTab={setActiveTab} tab="sold-items" name="Sold Items" />
            </div>

            {loading && <p className="status-msg">Loading orders...</p>}

            {!loading && orders.length === 0 && (
                <div className="empty-orders-container">
                    <p>{activeTab === 'my-orders'
                        ? "You haven't placed any orders yet."
                        : "You haven't sold any items yet."}</p>
                </div>
            )}

            {!loading && orders.length > 0 && (
                <div className="order-history-list">
                    {orders.map(order => (
                        <div
                            key={order.orderId}
                            className="order-history-row-card"
                            onClick={() => navigate(`/order-details/${order.orderId}`)}
                        >
                            <div className="order-history-info">
                                <h4>Order #{order.orderId}</h4>
                                <p className="order-history-meta">Date: {formatDate(order.dateTime)}</p>

                                <p className="order-history-meta">
                                    {activeTab === 'my-orders'
                                        ? "Customer: You"
                                        : `Buyer: ${order.username || "Unknown"}`}
                                </p>
                            </div>
                            <p className="order-history-price">${Number(order.totalAmount || order.totalPrice).toFixed(2)}</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}