package com.amazon.amazon_backend.controllerTest;

import com.amazon.amazon_backend.controller.CategoryController;
import com.amazon.amazon_backend.dto.CategoryResponse;
import com.amazon.amazon_backend.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService service;

    @InjectMocks
    private CategoryController categoryController; // 1. Instantiate the actual controller under test

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testGetByCategoryName_ShouldReturnCategory_WhenFound() throws Exception {
        String categoryName = "Books";
        CategoryResponse mockResponse = new CategoryResponse(categoryName);

        when(service.getByCategoryName(categoryName)).thenReturn(mockResponse);

        mockMvc.perform(get("/Category/name/" + categoryName)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.Category").value(categoryName))
                .andDo(print());
    }

}
