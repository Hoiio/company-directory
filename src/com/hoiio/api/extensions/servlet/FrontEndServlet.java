package com.hoiio.api.extensions.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.hoiio.api.extensions.persistence.Extension;
import com.hoiio.api.extensions.persistence.PMF;
import com.hoiio.api.extensions.util.StringUtil;

/**
 * FrontEndServlet provides all web services for the User Interface portion
 * 
 * @author ruprechtcua
 * 
 */
@SuppressWarnings("serial")
public class FrontEndServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(FrontEndServlet.class.getName()); 

	public void doGet(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException {
		String response = "";
		if (httpRequest.getRequestURI().contains("get")) {
			response = getExtension(httpRequest);
		}

		httpResponse.setContentType("application/json");
		httpResponse.getWriter().write(response);
	}
	
	public void doPost(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException {
		String response = "";
		if (httpRequest.getRequestURI().contains("update")) {
			response = updateExtension(httpRequest);
		}

		httpResponse.setContentType("application/json");
		httpResponse.getWriter().write(response);
	}
	
	private String updateExtension(HttpServletRequest req) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		//clear all extensions
		Query query = pm.newQuery(Extension.class);
		query.deletePersistentAll();
		
		//update new extensions
		String jsonExtensions = req.getParameter("extensions");
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<Extension> list = new ArrayList<Extension>();
		if(StringUtil.validString(jsonExtensions)){
			try{
				list = mapper.readValue(jsonExtensions, new TypeReference<List<Extension>>() {});
			}catch(Exception a){
				a.printStackTrace();
			}
		}

		for(Extension e:list){
			pm.makePersistent(e);
		}
		pm.close();
		
		return "{\"status\":\"success\"}";
	}

	private String getExtension(HttpServletRequest req) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Extension.class);

		// fetch from database
		List<Extension> results = new ArrayList<Extension>();
		try {
			results = (List<Extension>) q.execute();
		} finally {
			q.closeAll();
		}
		
		return StringUtil.objectToJSON(results);
	}
	
}
