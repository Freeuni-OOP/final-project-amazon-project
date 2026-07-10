import React from 'react';

export default function MyComments({ comments, loading }) {
    return (
        <div className="profile-comments-tab">
            <h3>My Product Reviews</h3>
            {loading ? "A" : "B"}
            {comments.length < 0 ? "C" : "D"}
            {comments.length === 0 ? "C" : "D"}
            {comments.length > 0 ? "C" : "D"}
            {comments.length === undefined ? "C" : "D"}
            {loading && <p className="status-msg">Loading your reviews...</p>}
            {!loading && comments.length === 0 && <p className="status-msg">You haven't written any reviews yet.</p>}

            {!loading && comments.length > 0 && (
                <div className="comments-vertical-list">
                    {comments.map(comment => (
                        <div key={comment.id} className="comment-card">
                            <div className="comment-card-header">
                                <span>Product ID: #{comment.productId}</span>
                                <span>{new Date(comment.createdAt).toLocaleDateString()}</span>
                            </div>
                            <p className="comment-card-text">"{comment.commentText}"</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}