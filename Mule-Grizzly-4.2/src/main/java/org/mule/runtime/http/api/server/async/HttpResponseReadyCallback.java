package org.mule.runtime.http.api.server.async;

import java.io.Writer;
import java.nio.charset.Charset;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class HttpResponseReadyCallback {

	@Trace(dispatcher=true)
	public void responseReady(final HttpResponse response, final ResponseStatusCallback responseStatusCallback) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpResponseReadyCallback",getClass().getSimpleName(),"responseReady"});
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Writer startResponse(final HttpResponse response, final ResponseStatusCallback responseStatusCallback,final Charset encoding) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpResponseReadyCallback",getClass().getSimpleName(),"startResponse"});
		return Weaver.callOriginal();
	}

}
