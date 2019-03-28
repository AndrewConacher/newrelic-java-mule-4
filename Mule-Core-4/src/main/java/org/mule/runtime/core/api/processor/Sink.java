package org.mule.runtime.core.api.processor;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Sink {


	@Trace
	public boolean emit(CoreEvent event) {
		boolean returned = Weaver.callOriginal();
		
		return returned;
	}
}
