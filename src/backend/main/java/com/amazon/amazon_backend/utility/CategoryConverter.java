package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.CategoryResponse;
import com.amazon.amazon_backend.model.Category;

public class CategoryConverter {
    public static CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(category.getCategoryId(), category.getCategoryName());
    }
}
