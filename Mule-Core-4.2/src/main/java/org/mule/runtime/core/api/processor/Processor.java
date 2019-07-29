package org.mule.runtime.core.api.processor;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.MuleUtils;

@Weave(type=MatchType.Interface)
public abstract class Processor {

	@Trace(async=true)
	public CoreEvent process(CoreEvent event) {
		
		String corrId1 = event.getCorrelationId();
		if(MuleUtils.hasToken(corrId1)) {
			MuleUtils.getToken(corrId1).link();
		}
		CoreEvent returnedEvent = Weaver.callOriginal();
		return returnedEvent;
	}
	
}
