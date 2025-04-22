package com.learn.productservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.productservice.exceptions.ProductAlreadyExistException;
import com.learn.productservice.exceptions.ProductNotFoundException;
import com.learn.productservice.model.Product;
import com.learn.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl  implements  ProductService{
	
	@Autowired
	private  ProductRepository   productRepository;

	@Override
	public List<Product> getAllProduct() {
 		return productRepository.findAll();
	}

	@Override
	public Product getProductById(int productId) {
		//boolean isExist= productRepository.existsById(productId);
		Optional<Product> opProduct = productRepository.findById(productId);
		Product  product = opProduct.isPresent()?opProduct.get():null;
 		return product;
	}

	@Override
	public Product addProduct(Product product) throws ProductAlreadyExistException {
	Optional<Product> opProduct=	 productRepository.findByProductNameAndPrice(product.getProductName(), product.getPrice());
		if (opProduct.isPresent()) {
			throw  new  ProductAlreadyExistException("Product Already Exist");
		}
		Product createdProduct = productRepository.save(product);
		
 		return createdProduct ;
	}

	@Override
	public boolean deleteProduct(int productId) throws ProductNotFoundException {
		boolean isDeleted =false;
		if (productRepository.existsById(productId)) {
			isDeleted=true;
			productRepository.deleteById(productId);
			
		}
		else {
			throw new  ProductNotFoundException("Invalid Product Id");
		}
		return isDeleted;
	}	
		
		
		/*
		 * if (productRepository.existsById(productId)==false) { throw new
		 * ProductNotFoundException("Invalid Product Id"); }
		 * productRepository.deleteById(productId); return true;
		 */	
	

	@Override
	public List<Product> getProductsBypriceGreaterThanEqual(int price) {
		List<Product> productList = productRepository.findByPriceGreaterThanEqual(price);
 		return productList;
	}

	@Override
	public List<Product> getProductsByProductName(String productName) {
 		return  productRepository.findByProductName(productName);
	}

	@Override
	public Product updateProduct(int productId, Product product) throws ProductNotFoundException {
 		if (productRepository.existsById(productId)==false) {
			throw new  ProductNotFoundException("Invalid Product Id");
		}
 		Product  existingProduct = productRepository.findById(productId).get();
 		existingProduct.setProductName(product.getProductName());
 		existingProduct.setPrice(product.getPrice());
 		
 		Product result =productRepository.save(existingProduct);
 		
 		return result;
 	}
	
	

}
