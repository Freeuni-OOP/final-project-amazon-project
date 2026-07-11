import '../App.css';
import {useState} from 'react';


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
        <div className="add-review-container" style={{ maxWidth: '600px', marginBottom: '40px', padding: '20px', border: '1px solid #e7e7e7', borderRadius: '8px', backgroundColor: '#fdfdfd' }}>
            <h3 style={{ marginTop: 0, marginBottom: '15px' }}>Review this product</h3>
            {error && <p style={{ color: '#c40000', fontSize: '14px' }}>{error}</p>}
            {success && (
                <div style={{ color: '#007600', backgroundColor: '#fcfff5', border: '1px solid #a3c293', padding: '15px', borderRadius: '4px', maxWidth: '600px', marginBottom: '40px' }}>
                    Thank you! Your review has been submitted successfully.
                </div>
            )}
            <form onSubmit={handleSubmitReview}>
                <div style={{ marginBottom: '15px', display: 'flex', alignItems: 'center', gap: '10px' }}>
                    <label style={{ fontWeight: '500' }}>Overall rating:</label>
                    <select
                        value={rating}
                        onChange={(e) => setRating(Number(e.target.value))}
                        style={{ padding: '5px 10px', borderRadius: '4px', border: '1px solid #ccd1d1' }}
                    >
                        {[5, 4, 3, 2, 1].map(num => <option key={num} value={num}>{num} Stars</option>)}
                    </select>
                </div>
                <div style={{ marginBottom: '15px' }}>
                    <label style={{ display: 'block', fontWeight: '500', marginBottom: '5px' }}>Add a written review:</label>
                    <textarea
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        placeholder="What did you like or dislike? What did you use this product for?"
                        rows="4"
                        style={{ width: '100%', padding: '10px', borderRadius: '4px', border: '1px solid #ccd1d1', boxSizing: 'border-box', fontFamily: 'inherit' }}
                    />
                </div>
                <button type="submit" className="amazon-primary-button" style={{ width: 'auto', padding: '8px 20px', margin: 0 }}>
                    Submit Review
                </button>
            </form>
        </div>
);
}

export default ReviewSection;