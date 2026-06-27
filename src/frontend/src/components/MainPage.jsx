import '../App.css';
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

function MainPage({requestedProducts}){
    const navigate = useNavigate();
    const [categories, setCategories] = useState([]);

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
            <nav id="navbar">
                <img src="/images/dark-logo.png" alt=""/>
                <input type="text" id="search-item" name="search" placeholder="Search Amazon ..."/>
                <div className="navbar-btns">
                    <button className="sign-in">Sign In</button>
                    <button className="sign-up">Sign Up</button>
                </div>
            </nav>

            <div id="categories">
                {categories.map((category) => {
                    return (
                        <a onClick={() => navigate(`/category/${category.categoryName}`)}
                           className="category">{category.categoryName}</a>
                    );
                })}
            </div>

            {requestedProducts}

            <div id="footer">

            </div>
        </div>
    );

}

export default MainPage;