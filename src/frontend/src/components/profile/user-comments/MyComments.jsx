import React from 'react';

export default function MyComments({ comments, loading }) {
    const renderStars = (rating) => {
            const totalStars = 5;
            const currentRating = rating || 0;
            return (
                <div className="comment-stars" style={{ color: '#ffb300', fontSize: '16px' }}>
                    {'★'.repeat(currentRating)}{'☆'.repeat(totalStars - currentRating)}
                </div>
            );
        };
    if(loading){
        return <p className="status-msg">Reviews are loading...</p>;
        }
    if(!comments||comments.length===0){
            return(
                <div className="empty-state-container" style={{ textAlign: 'center', padding: '40px 20px' }}>
                    <p className="status-msg">No reviews yet.</p>
                    </div>
                );
        }
    return (
        <div className="profile-comments-tab">
            <div className="comments-vertical-list" style={{ display: 'flex', flexDirection: 'column', gap: '16px', maxHeight: '520px', overflowY: 'auto', paddingRight: '8px' }}>
                    {comments.map(comment => (
                        <div key={comment.commentId} className="comment-card" style={{
                        backgroundColor: '#fff',
                        border: '1px solid #e7e7e7',
                        borderRadius: '8px',
                        padding: '20px',
                        display: 'flex',
                        flexDirection: 'column',
                        gap: '10px'
                    }}>
                            <div className="comment-card-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid #f0f2f2', paddingBottom: '8px' }}>
                                <span>Product ID: #{comment.productId}</span>

                                <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
                                    {renderStars(comment.rating)}
                                    <span style={{ fontSize: '13px', color: 'var(--Grayish-orange)' }}>
                                        {comment.createdAt? new Date(comment.createdAt).toLocaleDateString():''}
                                    </span>
                                </div>
                            </div>
                        <p className="comment-card-text" style={{ fontSize: '14px', color: 'var(--Very-dark-grayish-blue)', margin: '5px 0' }}>
                            "{comment.commentString}"
                        </p>
                        </div>
                    ))}
                </div>
        </div>
    );
}