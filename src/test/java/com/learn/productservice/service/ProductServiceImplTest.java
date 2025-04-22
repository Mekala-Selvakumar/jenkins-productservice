package com.learn.productservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.productservice.exceptions.ProductAlreadyExistException;
import com.learn.productservice.exceptions.ProductNotFoundException;
import com.learn.productservice.model.Product;
import com.learn.productservice.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productService;

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

	void testGetAllProduct() {
		List<Product> productList = List.of(product1, product2, product3, product4);
		when(productRepository.findAll()).thenReturn(productList);
		List<Product> actualList = productService.getAllProduct();
		assertEquals(productList, actualList);
	}

	@Test
	void testGetProductByIdSuccess() {
		Optional<Product> opProduct = Optional.of(product1);
		when(productRepository.findById(anyInt())).thenReturn(opProduct);
		Product product = productService.getProductById(5);
		assertEquals(product1, product);
	}

	@Test
	void testGetProductByIdFailure() {
		Optional<Product> opProduct = Optional.empty();
		when(productRepository.findById(anyInt())).thenReturn(opProduct);
		Product product = productService.getProductById(5888);
		assertNull(product);
		verify(productRepository, times(1)).findById(5888);
	}

	@Test
	void testAddProductSuccess() throws ProductAlreadyExistException {
		Optional<Product> opProduct = Optional.empty();
		when(productRepository.findByProductNameAndPrice(product1.getProductName(), product1.getPrice()))
				.thenReturn(opProduct);
		when(productRepository.save(product1)).thenReturn(product1);
		Product product = productService.addProduct(product1);
		assertNotNull(product);

		verify(productRepository, times(1)).findByProductNameAndPrice(product1.getProductName(), product1.getPrice());
		verify(productRepository, times(1)).save(product1);

	}

	@Test
	void testAddProductFailure() {
		Optional<Product> opProduct = Optional.of(product1);
		when(productRepository.findByProductNameAndPrice(product1.getProductName(), product1.getPrice()))
				.thenReturn(opProduct);
		assertThrows(ProductAlreadyExistException.class, () -> productService.addProduct(product1));
		verify(productRepository, times(1)).findByProductNameAndPrice(product1.getProductName(), product1.getPrice());

	}

	@Test
	void testDeleteProductSuccess() throws ProductNotFoundException {
		Optional<Product> opProduct = Optional.of(product1);
		when(productRepository.existsById(anyInt())).thenReturn(true);
		doNothing().when(productRepository).deleteById(product1.getProductId());
		boolean isDeleted = productService.deleteProduct(product1.getProductId());
		assertTrue(isDeleted);

		verify(productRepository, times(1)).existsById(anyInt());
		verify(productRepository, times(1)).deleteById(anyInt());
	}

	@Test
	void testDeleteProductFailure() {
		Optional<Product> opProduct = Optional.of(product1);
		when(productRepository.existsById(anyInt())).thenReturn(false);
		assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(product1.getProductId()));
		verify(productRepository, times(1)).existsById(anyInt());

	}

	@Test
	void testGetProductsBypriceGreaterThanEqual() {
		List<Product> productList = List.of(product2, product3);
		when(productRepository.findByPriceGreaterThanEqual(160)).thenReturn(productList);

		List<Product> result = productService.getProductsBypriceGreaterThanEqual(160);
		assertEquals(productList, result); // if the lists are expected to be the same order
		verify(productRepository, times(1)).findByPriceGreaterThanEqual(160);
		// assertThat(productList).containsAll(result);

		// assertIterableEquals(productList, result); // product - must implement
		// the hashcode and equals

	}

	@Test
	void testgetProductsByProductName() {
		List<Product> productList = List.of(product1, product2);

		when(productRepository.findByProductName(anyString())).thenReturn(productList);

		List<Product> result = productService.getProductsByProductName("Crayons");
		assertEquals(productList, result);

	}

}
