import '../App.css';
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import ReviewSection from './ReviewSection.jsx';
import ProductDetailsInfo from './ProductDetailsInfo.jsx';
import RelatedProducts from './RelatedProducts.jsx';

function ProductPage() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);
    const [selectedImageUrl, setSelectedImageUrl] = useState("");

    const storedUser = localStorage.getItem('user');
    const userObj = storedUser ? JSON.parse(storedUser) : null;
    const currentUserId = userObj?.id || localStorage.getItem('userId');

    useEffect(() => {
        const fetchProductData = async () => {
            window.scrollTo(0, 0);

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
        const newRatingValue = newComment.rating || 0;

        setProduct({
            ...product,
            canReview: false,
            top5comments: [newComment, ...(product.top5comments || [])],
            top5ratings: [newRatingValue, ...(product.top5ratings || [])]
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

    const renderIndividualStars = (rating) => {
        const roundedRating = Math.min(5, Math.max(0, Math.round(rating || 0)));
        return (
            <span>
                <span className="star-filled">{"★".repeat(roundedRating)}</span>
                <span className="star-empty">{"☆".repeat(5 - roundedRating)}</span>
            </span>
        );
    };

    if (!product) {
        return <div className="loading-container"><div className="spinner"></div></div>;
    }

    return (
        <div>
            <div className="product-page-main">
                <div className="product-images-side">
                    <div className="main-image-wrapper">
                        <img src={selectedImageUrl || "http://localhost:8080/photos/No-image-placeholder.png"} alt={product.productName}/>
                    </div>
                    <div className="thumbnail-list">
                        {product.imageUrls?.map((url, index) => (
                            <div className="thumbnail-images" key={index} onClick={() => setSelectedImageUrl(url)}>
                                <img className="thumbnail-img" src={url} alt="thumbnail" />
                            </div>
                        ))}
                    </div>
                    <hr/>
                    <div className="rating-section">
                        <h3 className="rating-title">Product Rating</h3>
                        {renderStars(product.rating || product.averageRating)}
                    </div>
                </div>

                <ProductDetailsInfo product={product} />
            </div>

            <div className="comments-section">
                <ReviewSection
                    productId={id}
                    currentUserId={currentUserId}
                    canReview={product.canReview}
                    onReviewSubmitted={handleReviewSubmitted}
                />

                <h2 className="comments-main-title">Top reviews from customers</h2>

                {product.top5comments && product.top5comments.length > 0 ? (
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                        {product.top5comments.map((comment, index) => {
                            const currentCommentRating = product.top5ratings?.[index] || 0;

                            return (
                                <div key={index} className="comment-card" style={{ padding: '15px', borderBottom: '1px solid #eee' }}>
                                    <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '8px' }}>
                                        <div className="comment-user-avatar"></div>
                                        <span className="comment-user-name" style={{ fontWeight: 'bold' }}>
                                              {comment.reviewerName || "Amazon Customer"}
                                        </span>

                                        {renderIndividualStars(currentCommentRating)}
                                    </div>

                                    <p className="comment-body-text" style={{ margin: '5px 0', color: '#333' }}>
                                        {typeof comment === 'string'
                                            ? comment
                                            : (comment.comment_STR || comment.comment || "No text available")}
                                    </p>
                                </div>
                            );
                        })}
                    </div>
                ) : (
                    <p className="no-comments-text">No reviews yet for this product. Be the first to review!</p>
                )}
                <hr style={{ border: '0', borderTop: '1px solid #e7e7e7', margin: '40px 0' }}/>

                <RelatedProducts
                    currentProductId={id}
                    productData={product}
                />
            </div>
        </div>
    );
}

export default ProductPage;