import '../App.css';
import {useEffect, useState} from "react";

function FilterComponent(){
    const [sortWith, setSortValue] = useState(() => {
        return localStorage.getItem("sort") || "default";
    });

    const [minValue, setMinValue] = useState(() => {
        return localStorage.getItem("min") || 0;
    });

    const [maxValue, setMaxValue] = useState(() => {
        return localStorage.getItem("max") || Infinity;
    });

    useEffect(() => {
        localStorage.setItem("sort", sortWith);
        localStorage.setItem("min", minValue);
        localStorage.setItem("max", maxValue);
        window.dispatchEvent(new Event("sort_changed"));
    }, [sortWith, minValue, maxValue]);

    return (
        <div className="filter">
            <div className="sort-options">
                <p>Sort Products with Price here: </p>
                <select value={sortWith} onChange={(e) => setSortValue(e.target.value)}>
                    <option value="default">Default</option>
                    <option value="price increase">Price Increase</option>
                    <option value="price decrease">Price Decrease</option>
                </select>
            </div>
            <div className="filter-controls">
                <form onSubmit={(e) => e.preventDefault()}>
                    <p>Set range of Price here: </p>
                    <input className="min" type="number" placeholder="Min Price" value={minValue} onChange={(e) => setMinValue(e.target.value)}/>
                    <input className="max" type="number" placeholder="Max Price" value={maxValue} onChange={(e) => setMaxValue(e.target.value)}/>
                </form>
            </div>
        </div>
    );
}

export default FilterComponent;