package com.learn.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.productservice.exceptions.ProductAlreadyExistException;
import com.learn.productservice.exceptions.ProductNotFoundException;
import com.learn.productservice.model.Product;
import com.learn.productservice.repository.ProductRepository;
import com.learn.productservice.service.ProductService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> productList = productService.getAllProduct();
		ResponseEntity<List<Product>> entity = new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
		return entity;
	}

	@PostMapping("/products")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		System.out.println("Inside...");
		Product createdProduct;
		ResponseEntity<?> entity;
		try {
			createdProduct = productService.addProduct(product);
			entity = new ResponseEntity<Product>(createdProduct, HttpStatus.CREATED);

		} catch (ProductAlreadyExistException e) {
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") int productId) {
		Product product = productService.getProductById(productId);
		ResponseEntity<?> entity;

		if (product == null) {
			entity = new ResponseEntity<String>("Product Not exist", HttpStatus.NOT_FOUND);
		} else {
			entity = new ResponseEntity<Product>(product, HttpStatus.OK);

		}
		return entity;
	}

	@GetMapping("/products/price/{price}")
	public ResponseEntity<?> getProductPriceGreaterThan(@PathVariable int price) {
		List<Product> productList = productService.getProductsBypriceGreaterThanEqual(price);
		ResponseEntity<?> entity = new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
		return entity;

	}

	@GetMapping("/products/name/{name}")
	public ResponseEntity<?> getProductByProductName(@PathVariable String name) {
		List<Product> productList = productService.getProductsByProductName(name);
		ResponseEntity<?> entity = new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
		return entity;
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable("id") int productId) throws ProductNotFoundException {
		boolean isDeleted = productService.deleteProduct(productId);
		ResponseEntity<?> entity = new ResponseEntity<String>("Product with Id " + productId + " Deleted Successfully",
				HttpStatus.OK);
		return entity;
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> updateProductById(@PathVariable("id") int productId, @RequestBody  Product product) throws ProductNotFoundException {
		Product  resultProduct = productService.updateProduct(productId, product);
		ResponseEntity<?> entity = new ResponseEntity<Product>(resultProduct,
				HttpStatus.OK);
		return entity;
	}
	
	
	
	
//	@ExceptionHandler(value = ProductNotFoundException.class)
//	public ResponseEntity<?> exceptionHandler(Exception  exp){
//		ResponseEntity<?> entity = new ResponseEntity<String>(exp.getMessage(),HttpStatus.BAD_REQUEST);
//		return entity;
//	}

}
