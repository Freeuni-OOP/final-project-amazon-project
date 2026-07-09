import '../App.css';
import Navbar from "./Navbar.jsx";
import CategoriesComponent from "./CategoriesComponent.jsx";
import FilterComponent from "./FilterComponent.jsx";

function MainPage({requestedProducts}){

    return (
        <div>
            <Navbar/>
            <FilterComponent />
            <CategoriesComponent />
            {requestedProducts}

            <div className="footer"></div>
        </div>
    );

}

export default MainPage;