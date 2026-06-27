package com.amazon.amazon_backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CategoryTest {

    @Test
    public void testCustomConstructorAndGetters() {
        Category category1 = new Category("Electronics");
        assertEquals("Electronics", category1.getCategoryName());
        assertNull(category1.getCategoryId());

        Category category2 = new Category("Home");
        assertEquals("Home", category2.getCategoryName());
        assertNull(category2.getCategoryId());

        Category category3 = new Category("Entertainment");
        assertEquals("Entertainment", category3.getCategoryName());
        assertNull(category3.getCategoryId());
    }

    @Test
    public void testNoArgsConstructor() {
        Category emptyCategory = new Category();
        assertNull(emptyCategory.getCategoryId());
        assertNull(emptyCategory.getCategoryName());
    }

}
