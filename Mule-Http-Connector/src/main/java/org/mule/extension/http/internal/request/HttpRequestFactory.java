package org.mule.extension.http.internal.request;

import org.mule.extension.http.api.request.HttpSendBodyMode;
import org.mule.extension.http.api.request.authentication.HttpRequestAuthentication;
import org.mule.extension.http.api.request.builder.HttpRequesterRequestBuilder;
import org.mule.extension.http.api.streaming.HttpStreamingType;
import org.mule.runtime.api.transformation.TransformationService;
import org.mule.runtime.api.util.MultiMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.mule.connectors.OutboundWrapper;

@Weave
public abstract class HttpRequestFactory {

	public HttpRequester create(HttpRequesterConfig config, String uri, String method, HttpStreamingType streamingMode,
            HttpSendBodyMode sendBodyMode, TransformationService transformationService,
            HttpRequesterRequestBuilder requestBuilder, HttpRequestAuthentication authentication) {
		MultiMap<String, String> headers = requestBuilder.getHeaders();
		OutboundWrapper wrapper = new OutboundWrapper(headers);
		NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(wrapper);
		requestBuilder.setHeaders(wrapper.getHeaders());
		return Weaver.callOriginal();
	}
}
