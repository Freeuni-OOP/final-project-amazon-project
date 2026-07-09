import '../App.css';
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import Navbar from "./Navbar.jsx";
import CategoriesComponent from "./CategoriesComponent.jsx";

function MainPage({ children }){
    const navigate = useNavigate();
    const [categories, setCategories] = useState([]);

    const [isLoggedIn, setIsLoggedIn] = useState(true);

    useEffect(() => {
        const fetchInitialData = async () => {
            try {
                const [categoryResponse] = await Promise.all([
                    fetch('http://localhost:8080/categories')
                ]);

                const categoriesData = await categoryResponse.json();
                setCategories(categoriesData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchInitialData();
    }, []);

    return (
        <div>
            <Navbar isLoggedIn={isLoggedIn} onLogout={() => setIsLoggedIn(false)} />
            <CategoriesComponent />

            <main className="main-content-layout">
                {children}
            </main>

            <div className="footer"></div>
        </div>
    );

}

export default MainPage;