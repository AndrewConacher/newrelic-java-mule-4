package org.mule.runtime.core.internal.event;

import com.newrelic.api.agent.weaver.Weave;

@Weave
public abstract class DefaultEventContext extends AbstractEventContext {


	
	
	@Weave
	private static class ChildEventContext {
		
	}
}
