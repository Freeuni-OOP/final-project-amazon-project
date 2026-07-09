import '../App.css';
import {useParams} from 'react-router-dom';
import {useEffect, useState} from "react";
import ProductsList from "./ProductsList.jsx";

function CategoryList(){
    const {categoryName} = useParams();
    const [productsRequested, setProductsRequested] = useState([]);

    useEffect(() => {
        const fetchInitialData = async () => {
            try {
                const productsResponse = await fetch(`http://localhost:8080/products/category-name/${categoryName}`);
                const productsData = await productsResponse.json();
                setProductsRequested(productsData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchInitialData();
    }, [categoryName]);

    return (<ProductsList allProducts={productsRequested} />);
}

export default CategoryList;