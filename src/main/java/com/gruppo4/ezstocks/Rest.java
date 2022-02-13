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
	ObjectMapper mapper = new ObjectMapper();
	
	public JsonNode restGet(String url) throws StreamReadException, DatabindException, MalformedURLException, IOException {
		// Creates a JsonNode Object from a .JSON
		return mapper.readValue(new URL(url), JsonNode.class);
	}
}
