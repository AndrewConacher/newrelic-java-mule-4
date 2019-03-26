package com.newrelic.instrumentation.mule.http;


import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

import java.util.logging.Level;

import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainContext.CompletionListener;

public class NRCompletionHandler implements CompletionListener {

	private Token token = null;
	private Segment segment = null;
	
	public NRCompletionHandler(Token t, Segment s) {
		token = t;
		segment = s;
	}

	@Override
	@Trace(async=true)
	public void onComplete(FilterChainContext arg0) {
		Exception e = new Exception("Completed Request");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "Finished request for {0}",arg0);
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		if(segment != null) {
			segment.end();
			segment = null;
		}
	}

}
