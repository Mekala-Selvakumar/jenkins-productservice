package com.learn.productservice.model;

import org.hibernate.annotations.Collate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product_master")
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@NoArgsConstructor
//@AllArgsConstructor
@Data
public class Product {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int productId;
	@Column(name="product_name" ,length = 50, nullable = false )
	private  String productName;
	private int price;
	public Product(String productName, int price) {
		super();
		this.productName = productName;
		this.price = price;
	}
	
	
	

}
