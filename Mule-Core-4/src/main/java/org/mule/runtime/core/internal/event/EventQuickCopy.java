package org.mule.runtime.core.internal.event;

import org.mule.runtime.core.internal.message.InternalEvent;
import org.mule.runtime.core.privileged.event.BaseEventContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;

@SuppressWarnings("serial")
@Weave
public abstract class EventQuickCopy implements InternalEvent {
	
	@NewField
	public Token token = null;
	
	public EventQuickCopy(final BaseEventContext context, final InternalEvent event) {
		token = null;
		if(EventQuickCopy.class.isInstance(event)) {
			EventQuickCopy tmp = (EventQuickCopy)event;
			if(tmp.token != null) {
				token = tmp.token;
				tmp.token = null;
			}
		} else if(DefaultEventBuilder.InternalEventImplementation.class.isInstance(event)) {
			DefaultEventBuilder.InternalEventImplementation tmp = (DefaultEventBuilder.InternalEventImplementation)event;
			if(tmp.token != null) {
				token = tmp.token;
				tmp.token = null;
			}
		}
		if(token == null) {
			token = NewRelic.getAgent().getTransaction().getToken();
		}
	}
}