package com.gruppo4.ezstocks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;


public abstract class Rest {
	// JSON-Object Mapper
	static ObjectMapper mapper = new ObjectMapper();
	
	public static JsonNode restGet(String url) throws StreamReadException, DatabindException, MalformedURLException, IOException {
		return mapper.readValue(new URL(url), JsonNode.class);
	}
}
