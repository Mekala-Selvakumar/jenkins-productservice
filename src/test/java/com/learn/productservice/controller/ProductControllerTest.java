package com.learn.productservice.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.productservice.exceptions.ProductAlreadyExistException;
import com.learn.productservice.exceptions.ProductNotFoundException;
import com.learn.productservice.model.Product;
import com.learn.productservice.service.ProductService;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	private Product product1;
	private Product product2;
	private Product product3;
	private Product product4;

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.openMocks(this);

		product1 = new Product("Crayons", 75);
		product2 = new Product("Crayons", 200);
		product3 = new Product("Water Color", 160);
		product4 = new Product("Color Pencil", 120);
	}

	@AfterEach
	void tearDown() throws Exception {

		product1 = null;
		product2 = null;
		product3 = null;
		product4 = null;
	}

	@Test
	void testGetAllProduct() throws Exception {
		List<Product> productList = List.of(product1,product2, product3);
		String listStr = new ObjectMapper().writeValueAsString(productList);
		when(productService.getAllProduct()).thenReturn(productList);

		mockMvc.perform(get("/api/v1/products")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(listStr));
		verify(productService, times(1)).getAllProduct();

 	}

	@Test
	void testAddProductSuccess() throws Exception {
		Product result = new Product(product1.getProductName(), product1.getPrice());
		result.setProductId(1);

		String productStr = new ObjectMapper().writeValueAsString(product1);
		String resultStr = new ObjectMapper().writeValueAsString(result);
		when(productService.addProduct(product1)).thenReturn(result);
		mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.content(productStr)).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().json(resultStr));
	}

	@Test
	void testAddProductFailure() throws Exception {

		String productStr = new ObjectMapper().writeValueAsString(product1);

		when(productService.addProduct(product1)).thenThrow(new ProductAlreadyExistException("Product Already Exist"));
		mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_ATOM_XML.APPLICATION_JSON)
				.content(productStr)).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Product Already Exist"));
	}

	@Test
	void testGetProductByIdSuccess() throws Exception {
		String productStr =new  ObjectMapper().writeValueAsString(product1);
		when(productService.getProductById(anyInt())).thenReturn(product1);
        mockMvc.perform(get("/api/v1/products/1"))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json(productStr));
     	}

	@Test
	void testGetProductByIdFailure() throws Exception {
		when(productService.getProductById(anyInt())).thenReturn(null);
		mockMvc.perform(get("/api/v1/products/1")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string("Product Not exist"));
	}

	@Test
	void testGetProductPriceGreaterThan() throws Exception {
		List<Product> productList = List.of(product2, product3);
		String listStr = new ObjectMapper().writeValueAsString(productList);
		when(productService.getProductsBypriceGreaterThanEqual(160)).thenReturn(productList);

		mockMvc.perform(get("/api/v1/products/price/160")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(listStr));
		verify(productService, times(1)).getProductsBypriceGreaterThanEqual(160);

	}

	@Test
	void testGetProductByProductName() throws Exception {
		List<Product> productList = List.of(product1, product2);
		String listStr = new ObjectMapper().writeValueAsString(productList);
		when(productService.getProductsByProductName("Crayons")).thenReturn(productList);

		mockMvc.perform(get("/api/v1/products/name/Crayons")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(listStr));

		verify(productService, times(1)).getProductsByProductName("Crayons");
	}

	@Test
	void testDeleteProductByIdSuccess() throws Exception {

		when(productService.deleteProduct(1)).thenReturn(true);
		mockMvc.perform(delete("/api/v1/products/1"))
		.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product with Id 1 Deleted Successfully"));
		verify(productService, times(1)).deleteProduct(1);

	}

	@Test
	void testDeleteProductByIdFailure() throws Exception {

		when(productService.deleteProduct(1)).thenThrow(new ProductNotFoundException("Invalid Product Id"));
		mockMvc.perform(delete("/api/v1/products/1")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Invalid Product Id"));
		verify(productService, times(1)).deleteProduct(1);

	}

//	@Test
//	void testUpdateProductById() {
//		fail("Not yet implemented");
//	}

}
