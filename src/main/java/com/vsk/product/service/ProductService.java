package com.vsk.product.service;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vsk.product.dto.Response;
import com.vsk.product.entity.Product;
import com.vsk.product.entity.ProductType;
import com.vsk.product.entity.repository.UserRepository;
import com.vsk.product.exception.InvalidInputException;
import com.vsk.product.exception.ResourceNotFountException;

@Service
public class ProductService {

	@Autowired
	private UserRepository productRepository;

	public Response<Product> create(Product product) throws Exception {
		// TODO Auto-generated method stub

		Optional<Product> existOptional = productRepository.findBySerialno(product.getSerialno());

		if (existOptional.isPresent()) {
			throw new InvalidInputException("Product already exists with serial no");
		}

		Product result = productRepository.saveAndFlush(product);

		Response<Product> userResponse = new Response.builder<Product>().message("Product created successfully")
				.status(true).result(result).build();

		return userResponse;
	}

	public Response<Product> getById(long id) throws Exception {
		// TODO Auto-genereturn null;rated method stub
		Optional<Product> result = productRepository.findById(id);

		if (!result.isPresent()) {
			throw new ResourceNotFountException("Product Not found");
		}

		Response<Product> userResponse = new Response.builder<Product>().message("Product fetched successfully")
				.status(true).result(result.get()).build();

		return userResponse;

	}

	public Response<List<Product>> getAll() throws Exception {
		// TODO Auto-generated method stub

		List<Product> userList = this.productRepository.findAll();
		Response<List<Product>> userResponse = new Response.builder<List<Product>>().status(true).result(userList)
				.build();

		return userResponse;
	}

	public Response<Product> update(Long id, Product product) throws Exception {
		// TODO Auto-generated method stub
		Optional<Product> existOptional = productRepository.findById(id);

		if (!existOptional.isPresent()) {
			throw new ResourceNotFountException("Product Not found");
		}

		Product updateProduct = existOptional.get();

		ProductType productType = updateProduct.getProducttype();
		productType.setName(product.getProducttype().getName());
		updateProduct.setProducttype(productType);

		updateProduct.setName(product.getName());
		updateProduct.setModel(product.getModel());
		updateProduct.setPrice(product.getPrice());
		updateProduct.setSerialno(product.getSerialno());
		updateProduct.setManufactor(product.getManufactor());

		Product updateResult = productRepository.saveAndFlush(updateProduct);

		Response<Product> updateResponse = new Response.builder<Product>().message("Product updated successfully")
				.status(true).result(updateResult).build();

		return updateResponse;

	}

	public Response<Void> delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Product> existOptional = productRepository.findById(id);

		if (!existOptional.isPresent()) {
			throw new ResourceNotFountException("Product Not found");
		}

		productRepository.delete(existOptional.get());
		Response<Void> userResponse = new Response.builder<Void>().message("Product deleted successfully").status(true)
				.build();
		return userResponse;

	}

}
