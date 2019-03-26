package org.mule.runtime.soap.api.client;

import org.mule.runtime.extension.api.soap.message.MessageDispatcher;
import org.mule.runtime.soap.api.message.SoapRequest;
import org.mule.runtime.soap.api.message.SoapResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class SoapClient {

	@Trace(dispatcher=true)
	public SoapResponse consume(SoapRequest request) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Mule","SoapClient",getClass().getSimpleName(),"consume",request.getOperation()});
		SoapResponse resp = Weaver.callOriginal();
		return resp;
	}
	
	@Trace(dispatcher=true)
	public SoapResponse consume(SoapRequest request, MessageDispatcher dispatcher) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Mule","SoapClient",getClass().getSimpleName(),"consume",request.getOperation()});
		SoapResponse resp = Weaver.callOriginal();
		return resp;
	}
}
