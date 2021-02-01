package com.masmovil.order.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {
		case 404:
			return new InstanceNotFoundException();
		case 409:
			return new InstanceAlreadyExistsException();
		default:

			HttpHeaders responseHeaders = new HttpHeaders();

			response.headers().entrySet().forEach(rh -> {
				responseHeaders.addAll(rh.getKey(), (List<? extends String>) rh.getValue());
			});

			try {
				return new RestClientResponseException("wrong request", response.status(), response.reason(),
						responseHeaders, new BufferedReader(new InputStreamReader(response.body().asInputStream())).lines()
								.collect(Collectors.joining("\n")).getBytes(),
						Charset.defaultCharset());
			} catch (IOException e) {
				return new RuntimeException ("comunication.error");
			}
		}
	}

}
