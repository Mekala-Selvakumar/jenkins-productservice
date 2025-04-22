package com.learn.productservice.service;

import java.util.List;

import com.learn.productservice.exceptions.ProductAlreadyExistException;
import com.learn.productservice.exceptions.ProductNotFoundException;
import com.learn.productservice.model.Product;

public interface ProductService {
	
	public  List<Product> getAllProduct();
	public  Product getProductById(int productId);
	public  Product  addProduct(Product product) throws ProductAlreadyExistException;
	public boolean deleteProduct(int productId) throws ProductNotFoundException;
	
	public  List<Product> getProductsBypriceGreaterThanEqual(int price);
	public List<Product>  getProductsByProductName(String productName);
	public  Product  updateProduct(int productId,  Product product) throws ProductNotFoundException;
	

}
