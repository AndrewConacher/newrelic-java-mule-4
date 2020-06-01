package org.mule.runtime.core.internal.execution;

import java.util.concurrent.CompletableFuture;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.core.api.construct.FlowConstruct;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.api.source.MessageSource;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.NRBiConsumer;

@Weave
public class FlowProcessMediator {

	@Trace
	public void process(FlowProcessTemplate template,MessageProcessContext messageProcessContext,PhaseResultNotifier phaseResultNotifier) {
		String location = "Unknown";
		MessageSource source = messageProcessContext.getMessageSource();
		if(source != null) {
			ComponentLocation componentLoc = source.getLocation();
			if(componentLoc != null) {
				String temp = componentLoc.getLocation();
				if(temp != null && !temp.isEmpty()) {
					location = temp;
				}
			}
		}
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","FlowProcessMediator","process",location);
		Weaver.callOriginal();
	}
	
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private CoreEvent createEvent(FlowProcessingPhaseTemplate template,
			MessageSource source, CompletableFuture<Void> responseCompletion,
			FlowConstruct flowConstruct) {

		NRBiConsumer nrConsumer = new NRBiConsumer(flowConstruct.getName() != null ? flowConstruct.getName() : null);
		responseCompletion = responseCompletion.whenComplete(nrConsumer);
		CoreEvent event = Weaver.callOriginal();
		return event;
	}

}
