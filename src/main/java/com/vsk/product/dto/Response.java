package com.vsk.product.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
@JsonSerialize(include = Inclusion.NON_NULL)
public class Response<T> {

	private T result;
	private String message;
	private boolean status;
	private List<String> errors;

	private Response() {

	}

	private Response(builder<T> builder) {
		this.result = builder.result;
		this.status = builder.status;
		this.message = builder.message;
		this.errors = builder.errors;
	}

	public static class builder<T> {

		private T result;
		private String message;
		private boolean status;
		private List<String> errors;

		public builder() {

		}

		public builder<T> message(String message) {
			this.message = message;
			return this;
		}

		public builder<T> result(T result) {
			this.result = result;
			return this;
		}

		public builder<T> status(boolean status) {
			this.status = status;
			return this;
		}

		public builder<T> errors(List<String> errors) {
			this.errors = errors;
			return this;
		}

		public Response<T> build() {
			return new Response<T>(this);
		}
	}
}
