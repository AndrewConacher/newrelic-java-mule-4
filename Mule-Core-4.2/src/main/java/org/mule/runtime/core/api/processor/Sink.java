package org.mule.runtime.core.api.processor;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Sink {

	@Trace(async=true,excludeFromTransactionTrace=true)
	public boolean emit(CoreEvent event) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Sink",getClass().getSimpleName(),"emit"});
		boolean returned = Weaver.callOriginal();
		
		return returned;
	}
	
	@Trace(async=true,excludeFromTransactionTrace=true)
	public void accept(final CoreEvent event) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Sink",getClass().getSimpleName(),"accept"});
		Weaver.callOriginal();
	}
}
