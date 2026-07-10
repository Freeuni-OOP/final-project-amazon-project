import '../App.css';
import { useState, useEffect } from 'react';
import {useNavigate} from "react-router-dom";

function Navbar(){
    const [searchQuery, setSearchQuery] = useState('');
    const [user, setUser]=useState(null);
    const navigate = useNavigate();

    useEffect(()=>{
        const storedUser=localStorage.getItem('user');
            if(storedUser){
                setUser(JSON.parse(storedUser));
            }
        },
    []);
    const submit = (e) => {
        e.preventDefault();
        if (searchQuery.trim()) {
            setSearchQuery("");
            navigate(`/search/${searchQuery}`);
        }
    }

    const handleSignOut= () =>{
        localStorage.removeItem('user');
        setUser(null);
        navigate('/sign-in');
    }

    return (
        <nav id="navbar">
            <img onClick={() => navigate("/")} src="/images/dark-logo.png" alt=""/>
            <form onSubmit={submit}>
                <input value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)}
                       type="text" name="search" className="search-item" placeholder="Search Products ..."/>
            </form>
            <div className="navbar-btns">
                {user ? (
                    <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
                        <span style={{ color: 'white', fontWeight: 'bold' }}>
                            Hello, {user.username}
                        </span>
                        <button onClick={handleSignOut} className="sign-out">
                            Sign Out
                        </button>
                    </div>
                    ):(
                <>
                    <button
                        onClick={() => navigate('/sign-in')}
                        className="sign-in">
                        Sign In
                    </button>

                    <button
                        onClick={() => navigate('/sign-up')}
                        className="sign-up">
                        Sign Up
                    </button>
                </>
                )}
            </div>
        </nav>
    );
}

export default Navbar;