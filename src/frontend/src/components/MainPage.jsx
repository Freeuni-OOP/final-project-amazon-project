import '../App.css';
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import Navbar from "./Navbar.jsx";

function MainPage({requestedProducts}){
    const navigate = useNavigate();
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        const fetchInitialData = async () => {
            try {
                const categoryResponse = await fetch('http://localhost:8080/categories');
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
            <Navbar/>

            <div className="categories">
                {categories.map((category) => {
                    return (
                        <a onClick={() => navigate(`/category-name/${category.categoryName}`)}
                           className="category">{category.categoryName}</a>
                    );
                })}
            </div>

            {requestedProducts}

            <div className="footer">

            </div>
        </div>
    );

}

export default MainPage;