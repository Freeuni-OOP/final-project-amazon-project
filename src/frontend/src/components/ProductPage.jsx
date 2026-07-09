import '../App.css';
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import ReviewSection from './ReviewSection.jsx';
import ProductDetailsInfo from './ProductDetailsInfo.jsx';

function ProductPage() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);
    const [selectedImageUrl, setSelectedImageUrl] = useState("");

    const storedUser = localStorage.getItem('user');
    const userObj = storedUser ? JSON.parse(storedUser) : null;
    const currentUserId = userObj?.id || localStorage.getItem('userId');

    useEffect(() => {
        const fetchProductData = async () => {
            try {
                const url = currentUserId
                    ? `http://localhost:8080/products/${id}?userId=${currentUserId}`
                    : `http://localhost:8080/products/${id}`;

                const response = await fetch(url);
                if (!response.ok) throw new Error("Failed to fetch product data");

                const data = await response.json();
                setProduct(data);

                if (data.imageUrls && data.imageUrls.length > 0) {
                    setSelectedImageUrl(data.imageUrls[0]);
                }
            } catch (error) {
                console.error("Error fetching product:", error);
            }
        };
        fetchProductData();
    }, [id]);

    const handleReviewSubmitted = (newComment) => {
        setProduct({
            ...product,
            canReview: false,
            top5comments: [newComment, ...(product.top5comments || [])].slice(0, 5)
        });
    };

    const renderStars = (rating) => {
        const roundedRating = Math.min(5, Math.max(0, Math.round(rating || 0)));
        return (
            <span className="rating-stars-wrapper">
                {"★".repeat(roundedRating)}{"☆".repeat(5 - roundedRating)}
                <span className="rating-text-count">
                    ({rating ? Number(rating).toFixed(1) : "0.0"} out of 5)
                </span>
            </span>
        );
    };

    if (!product) {
        return <div className="loading-container"><div className="spinner"></div></div>;
    }

    return (
        <div>
            <div className="product-page-main" style={{ display: 'flex', gap: '40px', padding: '20px', maxWidth: '1200px', margin: '0 auto' }}>
                <div className="product-images-side" style={{ flex: '1', maxWidth: '500px', display: 'flex', flexDirection: 'column' }}>
                    <div className="main-image-wrapper" style={{ border: '1px solid #f2f2f2', padding: '10px', borderRadius: '8px', display: 'flex', justifyContent: 'center', backgroundColor: '#fff' }}>
                        <img src={selectedImageUrl || "http://localhost:8080/photos/No-image-placeholder.png"} alt={product.productName} style={{ width: '100%', objectFit: 'contain', maxHeight: '400px' }} />
                    </div>
                    <div className="thumbnail-list" style={{ display: 'flex', gap: '10px', marginTop: '15px', flexWrap: 'wrap', justifyContent: 'center' }}>
                        {product.imageUrls?.map((url, index) => (
                            <div key={index} onClick={() => setSelectedImageUrl(url)} className={url === selectedImageUrl ? "thumbnail-item active" : "thumbnail-item"} style={{ width: '60px', height: '60px', borderRadius: '4px', cursor: 'pointer', padding: '2px', backgroundColor: '#fff', boxSizing: 'border-box' }}>
                                <img src={url} alt="thumbnail" style={{ width: '100%', height: '100%', objectFit: 'cover', borderRadius: '2px' }} />
                            </div>
                        ))}
                    </div>
                    <hr style={{ border: '0', borderTop: '1px solid #e7e7e7', margin: '20px 0' }} />
                    <div className="rating-section" style={{ padding: '0 10px' }}>
                        <h3 className="rating-title">Product Rating</h3>
                        {renderStars(product.averageRating)}
                    </div>
                </div>


                <ProductDetailsInfo product={product} />
            </div>

            <div className="comments-section" style={{ maxWidth: '1200px', margin: '40px auto', padding: '20px', borderTop: '1px solid #e7e7e7' }}>

                <ReviewSection
                    productId={id}
                    currentUserId={currentUserId}
                    canReview={product.canReview}
                    onReviewSubmitted={handleReviewSubmitted}
                />

                <h2 className="comments-main-title">Top reviews from customers</h2>
                {product.top5comments && product.top5comments.length > 0 ? (
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                        {product.top5comments.map((comment, index) => (
                            <div key={index} className="comment-card" style={{ padding: '15px', borderRadius: '8px' }}>
                                <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '8px' }}>
                                    <div className="comment-user-avatar" style={{ width: '25px', height: '25px', borderRadius: '50%' }}></div>
                                    <span className="comment-user-name">Amazon Customer</span>
                                </div>
                                <p className="comment-body-text" style={{ lineHeight: '1.4' }}>{comment}</p>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p className="no-comments-text">No reviews yet for this product. Be the first to review!</p>
                )}
            </div>
        </div>
    );
}

export default ProductPage;