import React from 'react';

export default function UserInfo({ userInfo }){
    return (
        <div className="info-grid">
            <p><strong>Username:</strong> {userInfo.username}</p>
            <p><strong>Email:</strong> {userInfo.email}</p>
            <p><strong>Account Balance:</strong> ${Number(userInfo.balance).toFixed(2)}</p>
            <p><strong>Gender:</strong> {userInfo.gender || 'Not specified'}</p>
            <p><strong>Birth Date:</strong> {new Date(userInfo.birthDate).toLocaleDateString()}</p>
        </div>
    );
}