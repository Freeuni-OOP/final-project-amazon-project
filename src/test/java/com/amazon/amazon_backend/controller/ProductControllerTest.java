package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.ProductRequest;
import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.ImagesUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.NameDescriptionUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.PriceUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.QuantityUpdateRequest;
import com.amazon.amazon_backend.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testGetProductById_ShouldReturnProduct_WhenFound() throws Exception {
        Integer productId = 1;
        ProductResponse mockResponse = new ProductResponse(productId, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);

        when(service.getProductById(productId, null)).thenReturn(mockResponse);

        mockMvc.perform(get("/products/" + productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andDo(print());
    }

    @Test
    public void testSearchProductsByName_ShouldReturnProducts_WhenFound() throws Exception {
        ProductResponse mockResponse = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);

        when(service.searchProductsByName("laptop")).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/products/search").param("name", "laptop")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Laptop"))
                .andDo(print());
    }

    @Test
    public void testGetProductsByCategoryId_ShouldReturnProducts_WhenFound() throws Exception {
        Integer categoryId = 1;
        ProductResponse mockResponse = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(), List.of(), false);

        when(service.searchProductsByCategoryId(categoryId)).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/products/category/" + categoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value("Electronics"))
                .andDo(print());
    }

    @Test
    public void testGetProductsByCategoryName_ShouldReturnProducts_WhenFound() throws Exception {
        String categoryName = "Electronics";
        ProductResponse mockResponse = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), categoryName, "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);

        when(service.searchProductsByCategoryName(categoryName)).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/products/category-name/" + categoryName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value(categoryName))
                .andDo(print());
    }

    @Test
    public void testGetProductsBySellerId_ShouldReturnProducts_WhenFound() throws Exception {
        Integer sellerId = 1;
        ProductResponse mockResponse = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO,List.of(), List.of(), false);

        when(service.searchProductsBySellerId(sellerId)).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/products/seller/" + sellerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sellerName").value("sellerUser"))
                .andDo(print());
    }

    @Test
    public void testGetAllProducts_ShouldReturnAllProducts() throws Exception {
        ProductResponse mockResponse = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO,List.of(), List.of(), false);

        when(service.getAllProducts()).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Laptop"))
                .andDo(print());
    }

    @Test
    public void testCreateProduct_ShouldReturnCreatedProduct1() throws Exception {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, 1, null, List.of("/photos/laptop.png"));
        ProductResponse mockResponse = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO,List.of(), List.of(), false);

        when(service.createProduct(any(ProductRequest.class))).thenReturn(mockResponse);

        MockMultipartFile emptyFile = new MockMultipartFile(
                "images",
                "",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                new byte[0]
        );

        MockMultipartFile mockProductPart = new MockMultipartFile(
                "product",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/products")
                        .file(mockProductPart)
                        .file(emptyFile)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(10))
                .andDo(print());
    }

    @Test
    public void testDeleteProduct_ShouldReturnSuccessMessage() throws Exception {
        Integer productId = 1;

        mockMvc.perform(delete("/products/" + productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Product deleted successfully."))
                .andDo(print());
    }

    @Test
    public void testUpdatePrice_ShouldReturnUpdatedProduct() throws Exception {
        Integer productId = 1;
        PriceUpdateRequest request = new PriceUpdateRequest(BigDecimal.valueOf(1199.99));
        ProductResponse mockResponse = new ProductResponse(productId, "Laptop", "Description", BigDecimal.valueOf(1199.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);

        when(service.updatePrice(productId, request)).thenReturn(mockResponse);

        mockMvc.perform(patch("/products/" + productId + "/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(1199.99))
                .andDo(print());
    }

    @Test
    public void testUpdateQuantity_ShouldReturnUpdatedProduct() throws Exception {
        Integer productId = 1;
        QuantityUpdateRequest request = new QuantityUpdateRequest(25);
        ProductResponse mockResponse = new ProductResponse(productId, "Laptop", "Description", BigDecimal.valueOf(999.99), 25, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);

        when(service.updateQuantity(productId, request)).thenReturn(mockResponse);

        mockMvc.perform(patch("/products/" + productId + "/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(25))
                .andDo(print());
    }

    @Test
    public void testUpdateImage_ShouldReturnUpdatedProduct() throws Exception {
        Integer productId = 1;
        ImagesUpdateRequest request = new ImagesUpdateRequest(List.of("/photos/new-laptop.png"));
        ProductResponse mockResponse = new ProductResponse(productId, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/new-laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);

        when(service.updateImage(eq(productId), any(ImagesUpdateRequest.class))).thenReturn(mockResponse);

        MockMultipartFile requestPart = new MockMultipartFile(
                "request",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile emptyFile = new MockMultipartFile(
                "images",
                "",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                new byte[0]
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, "/products/" + productId + "/image")
                        .file(requestPart)
                        .file(emptyFile)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageUrls[0]").value("/photos/new-laptop.png"))
                .andDo(print());
    }

    @Test
    public void testUpdateNameAndDescription_ShouldReturnUpdatedProduct() throws Exception {
        Integer productId = 1;
        NameDescriptionUpdateRequest request = new NameDescriptionUpdateRequest("Gaming Laptop", "Updated description");
        ProductResponse mockResponse = new ProductResponse(productId, "Gaming Laptop", "Updated description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);

        when(service.updateNameAndDescription(productId, request)).thenReturn(mockResponse);

        mockMvc.perform(patch("/products/" + productId + "/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Gaming Laptop"))
                .andDo(print());
    }
}