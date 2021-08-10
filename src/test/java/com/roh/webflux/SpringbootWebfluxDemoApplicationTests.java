package com.roh.webflux;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.roh.webflux.controller.ProductController;
import com.roh.webflux.dto.ProductDto;
import com.roh.webflux.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
class SpringbootWebfluxDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService productService;

	@Test
	public void addProductTest() {

		Mono<ProductDto> dto = Mono.just(new ProductDto("102", "mobile", 1, 1000));
		when(productService.saveProduct(dto)).thenReturn(dto);

		webTestClient.post().uri("/products/save").body(Mono.just(dto), ProductDto.class).exchange().expectStatus()
				.isOk(); // 200

	}

	@Test
	public void getProductsTest() {

		Flux<ProductDto> productDtoFlux = Flux.just(new ProductDto("102", "mobile", 1, 1000),
				new ProductDto("103", "laptop", 2, 2000));
		when(productService.getAllProducts()).thenReturn(productDtoFlux);

		Flux<ProductDto> response = webTestClient.get().uri("/products").exchange().expectStatus().isOk()
				.returnResult(ProductDto.class).getResponseBody(); // 200

		StepVerifier.create(response).expectSubscription().expectNext(new ProductDto("102", "mobile", 1, 1000))
				.expectNext(new ProductDto("103", "laptop", 2, 2000)).verifyComplete();
	}

	@Test
	public void getProductByIdTest() {

		Mono<ProductDto> dto = Mono.just(new ProductDto("102", "mobile", 1, 1000));
		when(productService.getProductById(ArgumentMatchers.any())).thenReturn(dto);

		Flux<ProductDto> response = webTestClient.get().uri("/products/102").exchange().expectStatus().isOk()
				.returnResult(ProductDto.class).getResponseBody();

		StepVerifier.create(response).expectSubscription().expectNextMatches(p -> p.getName().equals("mobile"))
				.verifyComplete();

	}

	@Test
	public void editProductDetails() {
		Mono<ProductDto> dto = Mono.just(new ProductDto("102", "mobile", 1, 1000));
		when(productService.updateProduct(dto, "102")).thenReturn(dto);

		webTestClient.put().uri("/products/update/102").body(Mono.just(dto), ProductDto.class).exchange().expectStatus()
				.isOk();
	}

	@Test
	public void deleteProductTest() {
		when(productService.deleteProduct(ArgumentMatchers.any())).thenReturn(Mono.empty());

		webTestClient.delete().uri("/products/delete/102").exchange().expectStatus().isOk();
	}

}
