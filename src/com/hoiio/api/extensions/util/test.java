package com.hoiio.api.extensions.util;

import java.util.List;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.hoiio.api.extensions.persistence.Extension;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String jsonExtensions = "{ \"key\":\"123\", \"forwardTo\":\"+65123343\"}";
		Extension extension = StringUtil.jsonToObject(jsonExtensions, Extension.class);
		
		System.out.println(extension.getKey() + " " + extension.getForwardTo());
		
		jsonExtensions = "[{ \"key\":\"123\", \"forwardTo\":\"+65123343\"},{ \"key\":\"123\", \"forwardTo\":\"+65123343\"}]";
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<Extension> list = null;
		if(StringUtil.validString(jsonExtensions)){
			try{
				list = mapper.readValue(jsonExtensions, new TypeReference<List<Extension>>() {});
			}catch(Exception a){
				a.printStackTrace();
			}
		}
		
		for(Extension e: list){
			System.out.println("array: "+e.getKey() + " " + e.getForwardTo());
		}
	}

}
