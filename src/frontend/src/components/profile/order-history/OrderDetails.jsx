import '../../../App.css';
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

function OrderDetails() {
    const { orderId } = useParams();
    const navigate = useNavigate();
    const [orderData, setOrderData] = useState(null);

    const storedUser = localStorage.getItem('user');
    const userObj = storedUser ? JSON.parse(storedUser) : null;
    const currentUserId = Number(userObj?.id || localStorage.getItem('userId'));

    useEffect(() => {
        const fetchOrderDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/orders/${orderId}`);
                const data = await response.json();
                setOrderData(data);
            } catch (error) {
                console.error("Error fetching order details:", error);
            }
        };
        fetchOrderDetails();
    }, [orderId]);

    if (!orderData) return <div style={{textAlign: 'center', padding: '50px', fontSize: '20px'}}>loading...</div>;

    const formatDate = (dateString) => {
        if (!dateString) return "";
        return dateString.replace('T', ' ').substring(0, 16);
    };

    const isBuyer = currentUserId === orderData.userId;

    const getImageUrl = (url) => {
        if (!url || url.trim() === "") return  'http://localhost:8080/photos/No-image-placeholder.png';
        if (url.startsWith('http')) return url;
        return `http://localhost:8080${url}`;
    };

    return (
        <div style={{ maxWidth: '800px', margin: '40px auto', padding: '30px', backgroundColor: 'var(--White)', borderRadius: '8px', border: '1px solid var(--Light-gray)' }}>
            <h2 style={{ color: 'var(--Very-dark-desaturated-blue-1)', marginBottom: '20px' }}>Order Details: #{orderData.orderId}</h2>

            <div style={{ lineHeight: '2' }}>
                <p><strong>Date:</strong> {formatDate(orderData.dateTime)}</p>

                <p>
                    <strong>{isBuyer ? "Customer:" : "Buyer:"}</strong> {isBuyer ? "You" : (orderData.username || "Unknown")}
                </p>

                <hr style={{ margin: '20px 0', borderColor: 'var(--Very-light-gray)' }} />

                <h3 style={{ marginBottom: '15px', color: 'var(--Very-dark-grayish-blue)' }}>Products List:</h3>

                {(orderData.items || []).map(item => (
                    <div key={item.productId} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid var(--Very-light-gray)', padding: '10px 0' }}>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
                            <img
                                src={getImageUrl(item.imageUrl)}
                                alt={item.productName}
                                onError={(e) => {
                                    e.target.onerror = null;
                                    e.target.src = "http://localhost:8080/pictures/No-image-placeholder.png";
                                }}
                                style={{
                                    width: '50px',
                                    height: '50px',
                                    objectFit: 'cover',
                                    borderRadius: '6px',
                                    border: '1px solid var(--Very-light-gray)'
                                }}
                            />
                            <div>
                                <p style={{ fontWeight: '500', margin: 0 }}>
                                    {item.productName} <span style={{ color: 'var(--Grayish-orange)' }}>(x{item.quantity})</span>
                                </p>
                                <p style={{ margin: 0, fontSize: '13px', color: 'gray' }}>
                                    {isBuyer
                                        ? `Seller: ${item.sellerName || 'Unknown'}`
                                        : `Buyer: ${orderData.username || 'Unknown'}`
                                    }
                                </p>
                            </div>
                        </div>
                        <p className="price" style={{ fontSize: '18px', margin: '0' }}>{item.amount}$</p>
                    </div>
                ))}

                <h2 style={{ textAlign: 'right', marginTop: '30px', color: 'var(--Red)' }}>Total: {orderData.totalAmount}$</h2>

                <button
                    onClick={() => navigate('/profile', { state: { defaultTab: 'transactions' } })}
                    style={{
                        marginTop: '20px',
                        padding: '10px 20px',
                        backgroundColor: 'var(--Desaturated-dark-blue-1)',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer'
                    }}
                >
                    Back to History
                </button>
            </div>
        </div>
    );
}

export default OrderDetails;