package org.mule.runtime.core.internal.event;

import org.mule.runtime.core.internal.message.InternalEvent;
import org.mule.runtime.core.privileged.store.DeserializationPostInitialisable;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;

@SuppressWarnings("deprecation")
@Weave
public abstract class DefaultEventBuilder {
	
	private InternalEvent originalEvent = Weaver.callOriginal();
	private boolean modified = Weaver.callOriginal();
	
	public InternalEvent build() {
		InternalEvent event = Weaver.callOriginal();
		
		if (this.originalEvent != null && !this.modified) {
			return event;
		}

		if(InternalEventImplementation.class.isInstance(event)) {
			InternalEventImplementation event2 = (InternalEventImplementation)event;
			if(event2.token == null) {
				event2.token = NewRelic.getAgent().getTransaction().getToken();
			}
		} else if(EventQuickCopy.class.isInstance(event)) {
			EventQuickCopy event2 = (EventQuickCopy)event;
			if(event2.token == null) {
				event2.token = NewRelic.getAgent().getTransaction().getToken();
			}
		}
		return event;
		
	}

	@SuppressWarnings("serial")
	@Weave
	public static abstract class InternalEventImplementation implements InternalEvent, DeserializationPostInitialisable {
		
		@NewField
		public Token token = null;
		
		@WeaveAllConstructors
		public InternalEventImplementation() {
		}
		
	}
}
