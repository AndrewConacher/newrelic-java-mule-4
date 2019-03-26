package org.mule.runtime.core.api.processor;

import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.MuleUtils;

@Weave(type=MatchType.Interface)
public abstract class Sink {


	@Trace
	public boolean emit(CoreEvent event) {
		boolean returned = Weaver.callOriginal();
		NewRelic.getAgent().getLogger().log(Level.FINE, "Sink.emit: {0}.emit({1}) returning {2}, {3}", getClass().getName(),event,returned,MuleUtils.hasToken(event) ? "has token"  :  "has not token");
		
		return returned;
	}
}
