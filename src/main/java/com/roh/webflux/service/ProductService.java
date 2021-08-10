package com.roh.webflux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.roh.webflux.dto.ProductDto;
import com.roh.webflux.repositiory.ProductRepository;
import com.roh.webflux.utils.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	public Flux<ProductDto> getAllProducts() {
		return productRepo.findAll().map(AppUtils::entityToDto);
	}

	public Mono<ProductDto> getProductById(String id) {
		return productRepo.findById(id).map(AppUtils::entityToDto);
	}

	public Flux<ProductDto> getProductInRange(double min, double max) {
		return productRepo.findByPriceBetween(Range.closed(min, max));
	}

	public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto) {
		return productDto.map(AppUtils::dtoToEntity).flatMap(productRepo::insert).map(AppUtils::entityToDto);
	}

	public Mono<ProductDto> updateProduct(Mono<ProductDto> productDto, String id) {
		return productRepo.findById(id).flatMap(e -> productDto.map(AppUtils::dtoToEntity).doOnNext(p -> p.setId(id)))
				.flatMap(productRepo::save).map(AppUtils::entityToDto);
	}
	
	public Mono<Void> deleteProduct(String id) {
		return productRepo.deleteById(id);
	}

}
