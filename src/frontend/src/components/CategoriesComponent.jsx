import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import '../App.css';

function CategoriesComponent(){
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
        <div className="categories">
            {categories.map((category) => {
                return (
                    <a onClick={() => navigate(`/category-name/${category.categoryName}`)}
                       className="category">{category.categoryName}</a>
                );
            })}
        </div>
    );
}


export default CategoriesComponent;