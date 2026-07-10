import React from 'react';
import UserInfo from "./UserInfo.jsx";

export default function AccountOverview({ userInfo }) {
    return (
        <div className="profile-info-tab">
            <h3>Account Overview</h3>
            <UserInfo userInfo={userInfo} />
        </div>
    );
}