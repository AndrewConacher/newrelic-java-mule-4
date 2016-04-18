package org.mule.api.registry;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class ObjectProcessor {
	@Trace(dispatcher=true)
	public abstract Object process(Object paramObject);
}
