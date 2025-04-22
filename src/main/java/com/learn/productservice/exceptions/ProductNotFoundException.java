package com.learn.productservice.exceptions;


//extends - Exception   - Checked
//extends  - RuntimeException  - unchecked
public class ProductNotFoundException  extends  Exception{

	public ProductNotFoundException() {
		super();
 	}

	public ProductNotFoundException(String message) {
		super(message);
 	}

	
	
}
