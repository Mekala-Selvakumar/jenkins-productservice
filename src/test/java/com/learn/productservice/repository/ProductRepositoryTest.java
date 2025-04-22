package com.learn.productservice.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.learn.productservice.model.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository  productRepository;
	
	private Product product1;
	private Product  product2;
	private Product  product3;
	private Product   product4;

	@BeforeEach
	void setUp() throws Exception {
		product1 = new Product("Crayons",75);
		product2 =new Product("Crayons",200);
		product3 =new Product("Water Color",160);
		product4 = new Product("Color Pencil",120);
	//	productRepository.deleteAll();
		
	}

	@AfterEach
	void tearDown() throws Exception {
		if (product1!=null) {
			productRepository.delete(product1);
		}
		if (product2!=null) {
			productRepository.delete(product2);
		}
		if (product3!=null) {
			productRepository.delete(product3);
		}
		if (product4!=null) {
			productRepository.delete(product4);
		}
		product1=null;

		product2=null;
		product3=null;
		product4=null;
	}

	
	@Test
	void testFindByProductName() {
		product1 =productRepository.save(product1);
		product2 =productRepository.save(product2);
		product3 =productRepository.save(product3);
		List<Product> expectedList =new ArrayList<>(List.of(product1,product2));
		List<Product> actualList = productRepository.findByProductName("Crayons");
	    boolean   result =actualList.containsAll(expectedList);
	    assertTrue(result);
	    
		
		//assertEquals(expectedList, actualList);
 	}
	
	
	@Test
	void testFindByProductNameAndPrice() {
		product1 =productRepository.save(product1);
		 Optional opProduct=productRepository.findByProductNameAndPrice(product1.getProductName(), product1.getPrice());
		 assertTrue(opProduct.isPresent());

 	}

	@Test
	void testFindByPriceGreaterThanEqual() {
		product1 =productRepository.save(product1);
		product2 =productRepository.save(product2);
		product3 =productRepository.save(product3);
		product4 =productRepository.save(product4);
		List<Product> expectedList =new ArrayList<>(List.of(product2,product3));
		List<Product> actualList = productRepository.findByPriceGreaterThanEqual(160);
	    boolean   result =actualList.containsAll(expectedList);
	    assertTrue(result);
		
 	}

	

	@Test
	void testFindAll() {
		product1 =productRepository.save(product1);
		product2 =productRepository.save(product2);
		product3 =productRepository.save(product3);
		List<Product> expectedList =new ArrayList<>(List.of(product1,product2,product3));
		List<Product> actualList = productRepository.findAll();
	    boolean   result =actualList.containsAll(expectedList);
	    assertTrue(result);

 	}

	@Test
	void testSave() {
		product1 = productRepository.save(product1);
		assertNotNull(product1);
  	}

	@Test
	void testFindByIdSuccess() {
		product1 = productRepository.save(product1);
		Optional<Product> opProduct = productRepository.findById(product1.getProductId());
		assertTrue(opProduct.isPresent());
 
 	}
	
	@Test
	void testFindByIdFailure() {
 		Optional<Product> opProduct = productRepository.findById(98999);
		assertTrue(opProduct.isEmpty());
  	}


	@Test
	void testExistsByIdSucess() {
		product1 = productRepository.save(product1);
		boolean isExist =productRepository.existsById(product1.getProductId());
		assertTrue(isExist);

 	}
	@Test
	void testExistsByIdFailure() {
 		boolean isExist =productRepository.existsById(98072);
		assertFalse(isExist);
 
 	}
	
	@Test
	void  testDeleteByIdSuccess() {
		product1 = productRepository.save(product1);
		productRepository.deleteById(product1.getProductId());
		boolean isExist =productRepository.existsById(product1.getProductId());
		assertFalse(isExist);
	}


}
