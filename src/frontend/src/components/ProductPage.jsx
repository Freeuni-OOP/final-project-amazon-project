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

    const getImageUrl = (url) => {
        if (!url || url.trim() === "") return 'http://localhost:8080/photos/No-image-placeholder.png';
        if (url.startsWith('http')) return url;
        return `http://localhost:8080${url}`;
    };

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
                <span className="star-filled">{"★".repeat(roundedRating)}</span>
                <span className="star-empty">{"☆".repeat(5 - roundedRating)}</span>
                <span className="rating-text-count" style={{ marginLeft: '5px', color: '#555', fontSize: '14px' }}>
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
                        <img src={getImageUrl(selectedImageUrl)} alt={product.productName}/>
                    </div>
                    <div className="thumbnail-list">
                    {product.imageUrls?.map((url, index) => (
                     <div className="thumbnail-images" key={index} onClick={() => setSelectedImageUrl(url)}>

                         <img className="thumbnail-img" src={getImageUrl(url)} alt="thumbnail" />
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
                    <div className="comments-list-container">
                        {product.top5comments.map((comment, index) => {
                            const currentCommentRating = product.top5ratings?.[index] || 0;

                            return (
                                <div key={index} className="comment-card">
                                    <div className="comment-user-header">
                                        <div className="comment-user-avatar"></div>
                                        <span className="comment-user-name">
                                          {comment.reviewerName || "Amazon Customer"}
                                    </span>

                                        {renderIndividualStars(currentCommentRating)}
                                    </div>

                                    <p className="comment-body-text">
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

                <hr className="section-divider"/>

                <RelatedProducts
                    currentProductId={id}
                    productData={product}
                />
            </div>
        </div>
    );
}

export default ProductPage;