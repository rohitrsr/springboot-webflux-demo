package com.roh.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roh.webflux.dto.ProductDto;
import com.roh.webflux.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public Flux<ProductDto> products() {
		return productService.getAllProducts();
		
	}
	
	@GetMapping("/{id}")
	public Mono<ProductDto> getProductById(@PathVariable String id) {
		return productService.getProductById(id);
	}
	
	@GetMapping("/inRange")
	public Flux<ProductDto> getProductInrange(@RequestParam("min") double min, @RequestParam("max") double max) {
		return productService.getProductInRange(min, max);
	}
	
	@PostMapping("/save")
	public Mono<ProductDto> saveProductDetail(@RequestBody Mono<ProductDto> dto) {
		return productService.saveProduct(dto);
	}
	
	@PutMapping("/update/{id}")
	public Mono<ProductDto> updateProductDetail(@RequestBody Mono<ProductDto> dto, @PathVariable("id") String id) {
		return productService.updateProduct(dto, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<Void> deleteProduct(@PathVariable("id") String id) {
		return productService.deleteProduct(id);
	}
	

}
