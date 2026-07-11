import '../App.css';
import { useState } from 'react';

function ReviewSection({ productId, currentUserId, canReview, onReviewSubmitted }) {
    const [comment, setComment] = useState("");
    const [rating, setRating] = useState(5);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState(false);

    const handleSubmitReview = async (e) => {
        e.preventDefault();
        if (!comment.trim()) {
            setError("Please write a comment.");
            return;
        }
        setError("");

        try {
            const response = await fetch(`http://localhost:8080/products/${productId}/reviews?userId=${currentUserId}`, {
            method: 'POST',
                headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                comment_STR: comment,
                rating: rating
            })
        });

        if (response.status === 403) {
            const errorMsg = await response.text();
            setError(errorMsg);
            return;
        }

        if (!response.ok) throw new Error("Server error");

        setSuccess(true);
        setComment("");

        onReviewSubmitted({
            comment: comment,
            rating: rating,
        });

    } catch (err) {
        setError("Failed to submit review. Please try again.");
        console.error(err);
    }
};

return (
    <div className="add-review-container">
        <h3 className="review-box-title">Review this product</h3>
        {error && <p className="review-error-text">{error}</p>}
        {success && (
            <div className="review-success-message">
                Thank you! Your review has been submitted successfully.
            </div>
        )}
        <form onSubmit={handleSubmitReview}>
            <div className="review-form-group-row">
                <label className="review-label">Overall rating:</label>
                <select
                    value={rating}
                    onChange={(e) => setRating(Number(e.target.value))}
                    className="review-select"
                >
                    {[5, 4, 3, 2, 1].map(num => <option key={num} value={num}>{num} Stars</option>)}
                </select>
            </div>
            <div className="review-form-group">
                <label className="review-label">Add a written review:</label>
                <textarea
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    placeholder="What did you like or dislike? What did you use this product for?"
                    rows="4"
                    className="review-textarea"
                />
            </div>
            <button type="submit" className="amazon-primary-button review-submit-btn">
                Submit Review
            </button>
        </form>
    </div>
);
}

export default ReviewSection;