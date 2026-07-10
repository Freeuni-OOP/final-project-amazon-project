import "../App.css";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";


function RelatedProducts({ currentProductId, productData }) {
    const [relatedProducts, setRelatedProducts] = useState([]);

    useEffect(() => {
        if (!productData) return;

        const categoryName = productData.categoryName || productData.category?.categoryName;
        const categoryId = productData.categoryId || productData.category?.categoryId || productData.category?.id;

        const fetchRelatedProducts = async () => {
            try {
                let url = "";
                if (categoryId) {
                    url = `http://localhost:8080/products/category/${categoryId}`;
                } else if (categoryName) {
                    url = `http://localhost:8080/products/category-name/${encodeURIComponent(categoryName)}`;
                } else {
                    return;
                }

                const response = await fetch(url);
                if (!response.ok) throw new Error("Failed to fetch related products");

                const data = await response.json();

                if (data && Array.isArray(data)) {
                    const filtered = data
                        .filter(prod => prod && String(prod.productId) !== String(currentProductId))
                        .slice(0, 10);
                    setRelatedProducts(filtered);
                }
            } catch (error) {
                console.error("Error fetching related products:", error);
            }
        };

        fetchRelatedProducts();
    }, [productData, currentProductId]);

    if (relatedProducts.length === 0) return null;

    return (
        <div className="related-products-container">
            <h2 className="related-products-title">
                Recommended products you might be interested in
            </h2>

            <div className="related-products-list">
                {relatedProducts.map((relProduct) => (
                    <div key={relProduct.productId} className="related-product-card">

                        <div className="related-product-image-wrapper">
                            <img
                                src={relProduct.imageUrls && relProduct.imageUrls[0] ? relProduct.imageUrls[0] : "http://localhost:8080/photos/No-image-placeholder.png"}
                                alt={relProduct.productName}
                            />
                        </div>

                        <div className="related-product-info">
                            <h4 className="related-product-name">
                                {relProduct.productName}
                            </h4>
                            <p className="related-product-price">
                                ${relProduct.price}
                            </p>

                            <Link to={`/product/${relProduct.productId}`} className="related-product-btn">
                                View Product
                            </Link>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default RelatedProducts;