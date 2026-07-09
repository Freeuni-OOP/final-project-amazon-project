import '../App.css';
import {useParams} from 'react-router-dom';
import ProductsList from "./ProductsList.jsx";

function SearchList({allProducts}){
    const {query} = useParams();
    const productsRequested = allProducts.filter(product => (product.categoryName.toLowerCase().includes(query.toLowerCase()) || product.productName.toLowerCase().includes(query.toLowerCase())));

    return (
        <ProductsList allProducts={productsRequested} />
    );

}

export default SearchList;