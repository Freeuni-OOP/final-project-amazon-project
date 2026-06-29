package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCategoryRepositoryOnIds(){
        Category category1 = new Category("Books");
        Category savedCategory1 = categoryRepository.save(category1);
        assertNotNull(savedCategory1.getCategoryId());

        Category category2 = new Category("Puzzles");
        Category savedCategory2 = categoryRepository.save(category2);
        assertNotNull(savedCategory2.getCategoryId());

        Category category3 = new Category("Sport");
        Category savedCategory3 = categoryRepository.save(category3);
        assertNotNull(savedCategory3.getCategoryId());
    }

    @Test
    public void testCategoryRepositoryOnCategoryNames(){
        Category category1 = new Category("House");
        Category savedCategory1 = categoryRepository.save(category1);
        assertEquals("House", savedCategory1.getCategoryName());

        Category category2 = new Category("Toys");
        Category savedCategory2 = categoryRepository.save(category2);
        assertEquals("Toys", savedCategory2.getCategoryName());

        Category category3 = new Category("Devices");
        Category savedCategory3 = categoryRepository.save(category3);
        assertEquals("Devices", savedCategory3.getCategoryName());
    }

    @Test
    public void testCategoryRepositoryOnCount(){
        assertEquals(0, categoryRepository.count());

        Category category1 = new Category("House");
        categoryRepository.save(category1);

        assertEquals(1, categoryRepository.count());

        Category category2 = new Category("Puzzles");
        categoryRepository.save(category2);

        assertEquals(2, categoryRepository.count());
    }

    @Test
    public void testCategoryRepositoryOnDelete(){
        Category category1 = new Category("House");
        Category savedCategory1 = categoryRepository.save(category1);
        Category category2 = new Category("Puzzles");
        Category savedCategory2 = categoryRepository.save(category2);

        assertEquals(2, categoryRepository.count());

        categoryRepository.deleteById(savedCategory1.getCategoryId());
        assertEquals(1, categoryRepository.count());

        categoryRepository.deleteById(savedCategory2.getCategoryId());
        assertEquals(0, categoryRepository.count());
    }

}
