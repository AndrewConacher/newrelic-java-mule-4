package org.mule.runtime.core.api.processor;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.MuleUtils;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import org.mule.runtime.core.api.construct.BackPressureReason;

@Weave(type=MatchType.Interface)
public abstract class Sink {

	@Trace(async=true,excludeFromTransactionTrace=true)
	public BackPressureReason emit(CoreEvent event) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Sink",getClass().getSimpleName(),"emit"});
		Token token = MuleUtils.getToken(event);
		if(token != null) {
			token.link();
		} else {
			token = NewRelic.getAgent().getTransaction().getToken();
			MuleUtils.setToken(event, token);
		}
		BackPressureReason returned = Weaver.callOriginal();
		
		return returned;
	}
	
	@Trace(async=true,excludeFromTransactionTrace=true)
	public void accept(final CoreEvent event) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Sink",getClass().getSimpleName(),"accept"});
		Token token = MuleUtils.getToken(event);
		if(token != null) {
			token.link();
		} else {
			token = NewRelic.getAgent().getTransaction().getToken();
			MuleUtils.setToken(event, token);
		}
		Weaver.callOriginal();
	}
}
