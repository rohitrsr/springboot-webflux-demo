package com.roh.webflux;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

	@Test
	public void testMono() {
		Mono<?> monoString = Mono.just("rohit")
				.then(Mono.error(new RuntimeException("Exception occurred")))
				.log();
		monoString.subscribe(System.out :: println, e -> System.out.println(e.getMessage()));
	}
	
	@Test
	public void testFlux() {
		Flux<?> fluxString = Flux.just("Spring", "SpringBoot", "Hibernate")
				.concatWithValues("AWS")
				.concatWith(Flux.error(new RuntimeException("Exception occurred")))
				.concatWithValues("Microservice")
				.log();
		fluxString.subscribe(System.out :: println);
	}
}
