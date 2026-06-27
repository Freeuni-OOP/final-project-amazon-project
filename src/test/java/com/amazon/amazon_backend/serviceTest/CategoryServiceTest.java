package com.amazon.amazon_backend.serviceTest;

import com.amazon.amazon_backend.dto.CategoryResponse;
import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.repository.CategoryRepository;
import com.amazon.amazon_backend.service.CategoryService;
import com.amazon.amazon_backend.utility.CategoryConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByCategoryName(){
        String categoryName1 = "Puzzles";
        Category category1 = new Category(categoryName1);
        CategoryResponse response1 = new CategoryResponse(1, categoryName1);

        String categoryName2 = "Sport";
        Category category2 = new Category(categoryName2);
        CategoryResponse response2 = new CategoryResponse(2, categoryName2);

        when(categoryRepository.findByCategoryName(categoryName1)).thenReturn(Optional.of(category1));
        when(categoryRepository.findByCategoryName(categoryName2)).thenReturn(Optional.of(category2));

        try (MockedStatic<CategoryConverter> converter = Mockito.mockStatic(CategoryConverter.class)) {
            converter.when(() -> CategoryConverter.toCategoryResponse(category1)).thenReturn(response1);

            CategoryResponse realResponse1 = categoryService.getByCategoryName(categoryName1);

            assertNotNull(realResponse1);
            assertTrue(categoryName1.equals(realResponse1.getCategoryName()));
            assertFalse(categoryName2.equals(realResponse1.getCategoryName()));
        }

        try (MockedStatic<CategoryConverter> converter = Mockito.mockStatic(CategoryConverter.class)){
            converter.when(() -> CategoryConverter.toCategoryResponse(category2)).thenReturn(response2);

            CategoryResponse realResponse2 = categoryService.getByCategoryName(categoryName2);

            assertNotNull(realResponse2);
            assertTrue(categoryName2.equals(realResponse2.getCategoryName()));
            assertFalse(categoryName1.equals(realResponse2.getCategoryName()));
        }

    }

}
