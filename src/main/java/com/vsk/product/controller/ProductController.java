package com.vsk.product.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.vsk.product.dto.Response;
import com.vsk.product.entity.Product;
import com.vsk.product.exception.InvalidInputException;
import com.vsk.product.exception.ServiceFailureException;
import com.vsk.product.service.ProductService;
import com.vsk.product.service.ProductService;

@RestController
@RequestMapping(path = "/v1")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping(path = "/product", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response<Product>> create(@RequestBody @Valid Product product, BindingResult bindingResult)
			throws Exception {

		if (bindingResult.hasErrors()) {
			throw new InvalidInputException("Invalid input fields value", bindingResult.getAllErrors());
		}

		Response<Product> response = productService.create(product);
		return ResponseEntity.ok(response);

	}

	@PutMapping(path = "/product/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response<Product>> update(@PathVariable("id") Long id, @RequestBody @Valid Product product,
			BindingResult bindingResult) throws Exception {

		if (bindingResult.hasErrors()) {
			throw new InvalidInputException("Invalid input fields value", bindingResult.getAllErrors());
		}

		Response<Product> response = productService.update(id, product);
		return ResponseEntity.ok(response);

	}

	@GetMapping(path = "/product/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response<Product>> getById(@PathVariable("id") Long id) throws Exception {

		Response<Product> response = productService.getById(id);
		return ResponseEntity.ok(response);

	}

	@GetMapping(path = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response<List<Product>>> getAll() throws Exception {

		Response<List<Product>> response = productService.getAll();
		return ResponseEntity.ok(response);

	}

	@DeleteMapping(path = "/product/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response<Void>> delete(@PathVariable("id") Long id) throws Exception {

		Response<Void> response = productService.delete(id);
		return ResponseEntity.ok(response);

	}

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@GetMapping(path = "/product/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@HystrixCommand(fallbackMethod = "fallbackmethod")
	public ResponseEntity<Response<String>> gettest() throws Exception {

		ServiceInstance serviceInstance = loadBalancerClient.choose("customer-service");

		URI uri = serviceInstance.getUri();

		String string = restTemplate.getForObject(uri + "/customer-service/v1/customer/test", toString().getClass());

		System.out.println(string);
		Response<String> response = new Response.builder<String>().message(string).build();
		return ResponseEntity.ok(response);

	}

	public ResponseEntity<Response<String>> fallbackmethod() {

		Response<String> response = new Response.builder<String>().message("Service is failure").build();
		return ResponseEntity.ok(response);

	}

}
