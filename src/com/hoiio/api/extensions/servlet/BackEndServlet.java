package com.hoiio.api.extensions.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.*;

import com.hoiio.api.extensions.persistence.Configuration;
import com.hoiio.api.extensions.persistence.Extension;
import com.hoiio.api.extensions.persistence.PMF;
import com.hoiio.sdk.Hoiio;
import com.hoiio.sdk.exception.HoiioException;
import com.hoiio.sdk.objects.enums.TransferOnFailture;

/**
 * BackEndServlet handles all services which regards to incoming calls, gather,
 * transfer
 * 
 * @author ruprechtcua
 * 
 */
@SuppressWarnings("serial")
public class BackEndServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(BackEndServlet.class
			.getName());

	private static final String URI_ANSWER = "answer";
	private static final String URI_GATHER = "gather";

	private static final String PARAM_SESSION = "session";
	private static final String PARAM_DIGITS = "digits";

	public void doPost(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException {
		String response = "";
		if (httpRequest.getRequestURI().contains(URI_ANSWER)) {
			response = answer(httpRequest);
		} else if (httpRequest.getRequestURI().contains(URI_GATHER)) {
			response = gather(httpRequest);
		}

		httpResponse.setContentType("application/json");
		httpResponse.getWriter().println(response);
	}

	private String answer(HttpServletRequest req) {
		// Create hoiio service
		Hoiio hoiio = new Hoiio(Configuration.getAppid(),
				Configuration.getAccesstoken());
		String sessionId = req.getParameter(PARAM_SESSION);
		try {
			hoiio.getIvrService().gather(sessionId,
					Configuration.getAppSpotUrl() + "/in/gather",
					Configuration.getMessage(), Configuration.getNumOfDigits(), null, 3, null);
		} catch (HoiioException e) {
			// This is thrown when the request doesn't return success_ok
			log.warning(sessionId + ": " +e.getStatus().toString());
			log.warning(sessionId + ": " +e.getContent());
			return e.getStatus().toString();
		}
		return "success";
	}

	private String gather(HttpServletRequest req) {
		// Create hoiio service
		Hoiio hoiio = new Hoiio(Configuration.getAppid(),
				Configuration.getAccesstoken());
		String digits = req.getParameter(PARAM_DIGITS);
		String sessionId = req.getParameter(PARAM_SESSION);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Extension.class);
		q.setFilter("key == keyParam");
		q.declareParameters("String keyParam");

		String forwardTo = "";
		try {
			List<Extension> results = (List<Extension>) q.execute(digits);
			if (!results.isEmpty()) {
				forwardTo = results.get(0).getForwardTo();
			} else {
				return "no mapping found";
			}
		} finally {
			q.closeAll();
		}

		try {
			hoiio.getIvrService().transfer(sessionId, forwardTo,
					Configuration.getMessageWhenForwarding(), null,
					TransferOnFailture.HANGUP, null, null);
		} catch (HoiioException e) {
			// This is thrown when the request doesn't return success_ok
			log.severe(e.getStatus().toString());
			log.severe(e.getContent());
			return e.getStatus().toString();
		}
		return "success";
	}
}
