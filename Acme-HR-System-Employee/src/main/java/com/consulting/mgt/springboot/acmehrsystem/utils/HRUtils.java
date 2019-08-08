package com.consulting.mgt.springboot.acmehrsystem.utils;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.consulting.mgt.springboot.acmehrsystem.model.Workstation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HRUtils {

	private static int nextEmployeeNumber = 0;
	private static int nextFacilitiesSerialNumber = 100;
	private static ObjectMapper mapper = new ObjectMapper();

	public static String nextEmployeeNumber() {

		return String.format("%05d", ++nextEmployeeNumber);
	}
	
	public static String nextFacilitiesSerialNumber() {

		return String.format("%05d", ++nextFacilitiesSerialNumber);
	}
	
	/**
	 * 
	 * @param method
	 * @param url
	 * @param body
	 * @return
	 */
	public static <T>Object sendRequest(HttpMethod method, String url, String body, Class<T> clazz) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> response = null;
		try {
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<String> request = new HttpEntity<>(body, headers);
			response = restTemplate.exchange(url, method, request, String.class);
		} catch(RuntimeException e) {
			if (e instanceof HttpClientErrorException)
				return null;
			else
				System.out.println(e);
		}
		return jsonToObject(response.getBody(), clazz);
	}
	
	public static String objectToJson(Object object) {
		String result = null;
		try {
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			System.out.println(e);
		}
		return result;
	}
	
	public static <T> Object jsonToObject(String json, Class<T> clazz) {
		Object result = null;
		try {
			if(clazz.isInterface())
				result = mapper.readValue(json, new TypeReference<List<Workstation>>() {});
			else
				result = mapper.readValue(json, clazz);
		} catch (IOException e) {
			System.out.println(e);
		}
		return result;
	}
	
}
