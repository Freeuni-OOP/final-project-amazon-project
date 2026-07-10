import '../App.css';
import {useEffect, useState} from 'react';
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

    const [isDark, setIsDark] = useState(() => {
        return localStorage.getItem("theme") === "dark";
    });

    function toDarkMode(darkMode){
        document.querySelector('.slider')?.classList.toggle("change-position", darkMode);
        document.querySelector('.footer')?.classList.toggle("footer-dark", darkMode);
        document.querySelector('.categories')?.classList.toggle("categories-dark", darkMode);
        document.querySelector('.sign-up')?.classList.toggle("sign-up-dark", darkMode);
        document.querySelector('.navbar')?.classList.toggle("navbar-dark", darkMode);
        document.querySelector('body')?.classList.toggle("body-dark", darkMode);

        document.querySelectorAll('.quantity, .category, .seller').forEach((element) => {
            element.classList.toggle("dark-product-txt", darkMode);
        });

        document.querySelectorAll('.price').forEach((element) => {
            element.classList.toggle("price-dark", darkMode);
        });

        document.querySelectorAll('.productName').forEach((element) => {
            element.classList.toggle("productName-dark", darkMode);
        });

        document.querySelectorAll('.product').forEach((element) => {
            element.classList.toggle("product-dark", darkMode);
        });
    }

    useEffect(() => {
        localStorage.setItem("theme", isDark ? "dark" : "light");
        toDarkMode(isDark);
        if (isDark) {
            const observer = new MutationObserver(() => {
                toDarkMode(true);
            });

            observer.observe(document.body, { childList: true, subtree: true });
            return () => observer.disconnect();
        }
    }, [isDark]);

    return (
        <nav className="navbar">
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
            <div className="switch">
                <div className="slider-bg">
                    <button onClick={() => setIsDark(!isDark)} className="slider"></button>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;