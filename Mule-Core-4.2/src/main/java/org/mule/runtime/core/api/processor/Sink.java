package org.mule.runtime.core.api.processor;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.MuleUtils;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Sink {

	@Trace(async=true)
	public boolean emit(CoreEvent event) {
		Token token = MuleUtils.getToken(event);
		if(token != null) {
			token.link();
		}
		boolean returned = Weaver.callOriginal();
		
		return returned;
	}
}
