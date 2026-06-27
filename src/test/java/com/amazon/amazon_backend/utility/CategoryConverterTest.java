package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.CategoryResponse;
import com.amazon.amazon_backend.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryConverterTest {

    private CategoryConverter converter;

    @BeforeEach
    public void setUp(){
        converter = new CategoryConverter();
    }

    @Test
    public void testToCategoryResponseOnTrueCases(){
        Category category = new Category("House");
        CategoryResponse response = converter.toCategoryResponse(category);

        assertNotNull(response);
        assertEquals("House", response.getCategory());
        assertEquals(category.getCategoryName(), response.getCategory());
    }

    @Test
    public void testToCategoryResponseOnFalseCases(){
        Category category1 = new Category("House");
        Category category2 = new Category("Sport");
        Category category3 = new Category("Books");

        CategoryResponse response1 = converter.toCategoryResponse(category1);
        CategoryResponse response2 = converter.toCategoryResponse(category2);
        CategoryResponse response3 = converter.toCategoryResponse(category3);

        assertNotEquals(category1.getCategoryName(), response2.getCategory());
        assertNotEquals(category2.getCategoryName(), response3.getCategory());
        assertNotEquals(category3.getCategoryName(), response1.getCategory());
    }

}
