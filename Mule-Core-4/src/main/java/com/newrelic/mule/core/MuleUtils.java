package com.newrelic.mule.core;

import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.DefaultEventBuilder.InternalEventImplementation;
import org.mule.runtime.core.internal.event.EventQuickCopy;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

public class MuleUtils {

	
	public static boolean hasToken(CoreEvent event) {
		if(EventQuickCopy.class.isInstance(event)) {
			EventQuickCopy event2 = (EventQuickCopy)event;
			return event2.token != null;
		} else if(InternalEventImplementation.class.isInstance(event)) {
			InternalEventImplementation event2 = (InternalEventImplementation)event;
			return event2.token != null;
		}
		NewRelic.getAgent().getLogger().log(Level.FINE, new Exception("Unknown CoreEvent type"), "The CoreEvent {0} is of unknown type", event);
		return false;
	}
	
	public static Token getToken(CoreEvent event) {
		if(event != null) {
			if(EventQuickCopy.class.isInstance(event)) {
				EventQuickCopy event2 = (EventQuickCopy)event;
				return event2.token;
			} else if(InternalEventImplementation.class.isInstance(event)) {
				InternalEventImplementation event2 = (InternalEventImplementation)event;
				return event2.token;
			}
		}
		return null;
	}
	
	public static void removeToken(CoreEvent event) {
		if(EventQuickCopy.class.isInstance(event)) {
			EventQuickCopy event2 = (EventQuickCopy)event;
			event2.token = null;
		} else if(InternalEventImplementation.class.isInstance(event)) {
			InternalEventImplementation event2 = (InternalEventImplementation)event;
			event2.token = null;
		}
		
	}
	
	public static boolean addToken(CoreEvent event, Token token) {
		boolean result = false;
		if(EventQuickCopy.class.isInstance(event)) {
			EventQuickCopy event2 = (EventQuickCopy)event;
			event2.token = token;
			result = true;
		} else if(InternalEventImplementation.class.isInstance(event)) {
			InternalEventImplementation event2 = (InternalEventImplementation)event;
			event2.token = token;
			result = true;
		}
		
		return result;
	}
}
