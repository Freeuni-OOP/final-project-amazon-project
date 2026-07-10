import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TabButton from "../TabButton.jsx";

export default function OrderHistory() {
    const [activeTab, setActiveTab] = useState('my-orders');
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    const [currentPage, setCurrentPage] = useState(1);
        const itemsPerPage = 5;
    const navigate = useNavigate();

    const storedUser = localStorage.getItem('user');
    const userObj = storedUser ? JSON.parse(storedUser) : null;
    const currentUserId = userObj?.id || localStorage.getItem('userId');

    useEffect(() => {
        setCurrentPage(1);
    }, [activeTab]);

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

    const indexOfLastOrder = currentPage * itemsPerPage;
    const indexOfFirstOrder = indexOfLastOrder - itemsPerPage;
    const currentOrders = orders.slice(indexOfFirstOrder, indexOfLastOrder);

    const totalPages = Math.ceil(orders.length / itemsPerPage);

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
                    {currentOrders.map(order => (
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
            {!loading && totalPages > 1 && (
                       <div style={{ display: 'flex', justifyContent: 'center', gap: '8px', marginTop: '20px' }}>
                           {Array.from({ length: totalPages }, (_, i) => i + 1).map(pageNumber => (
                               <button
                                   key={pageNumber}
                                   onClick={() => setCurrentPage(pageNumber)}
                                   style={{
                                       padding: '6px 12px',
                                       border: '1px solid var(--Grayish-orange, #e47911)',
                                       backgroundColor: currentPage === pageNumber ? 'var(--Grayish-orange, #e47911)' : '#fff',
                                       color: currentPage === pageNumber ? '#fff' : '#333',
                                       borderRadius: '4px',
                                       cursor: 'pointer',
                                       fontWeight: currentPage === pageNumber ? 'bold' : 'normal',
                                       transition: 'all 0.2s ease'
                                   }}
                               >
                                   {pageNumber}
                               </button>
                           ))}
                       </div>
                   )}
               </div>
           );
       }