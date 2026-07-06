import '../App.css';
import { useState } from 'react';
import {useNavigate} from "react-router-dom";

function Navbar({ isLoggedIn, onLogout }){
    const [searchQuery, setSearchQuery] = useState('');
    const navigate = useNavigate();

    const submit = (e) => {
        e.preventDefault();
        if (searchQuery.trim()) {
            setSearchQuery("");
            navigate(`/search/${searchQuery}`);
        }
    }

    return (
        <nav id="navbar">
            <img onClick={() => navigate("/")} src="/images/dark-logo.png" alt=""/>
            <form onSubmit={submit}>
                <input value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)}
                       type="text" name="search" className="search-item" placeholder="Search Products ..."/>
            </form>
            <div className="navbar-btns">
                {isLoggedIn ? (
                    <>
                        <button className="profile-btn" onClick={() => navigate("/profile")}>
                            My Account
                        </button>
                        <button className="sign-out-btn" onClick={onLogout}>
                            Sign Out
                        </button>
                    </>
                ) : (
                    <>
                        <button className="sign-in">Sign In</button>
                        <button className="sign-up">Sign Up</button>
                    </>
                )}
            </div>
        </nav>
    );
}

export default Navbar;