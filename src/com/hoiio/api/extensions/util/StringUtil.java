package com.hoiio.api.extensions.util;

import java.io.StringWriter;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

public class StringUtil {
	public static <T> T jsonToObject(String json, Class<T> classType){
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T obj = null;
		if(validString(json)){
			try{
				obj = mapper.readValue(json, classType);
			}catch(Exception a){
				a.printStackTrace();
				return null;
			}
		}
		return obj;
	}
	
	public static <T> String objectToJSON(T obj){
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, obj);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			return null;
		}
		
		return writer.toString();
	}
	
	public static boolean validString(String s){
		if(s == null)
			return false;
		if(s.trim().isEmpty())
			return false;
				
		return true;
	}
}
