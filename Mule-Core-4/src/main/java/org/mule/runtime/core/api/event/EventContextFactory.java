package org.mule.runtime.core.api.event;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.event.EventContext;
import org.mule.runtime.core.api.construct.FlowConstruct;
import org.mule.runtime.core.api.exception.FlowExceptionHandler;
import org.mule.runtime.core.privileged.event.BaseEventContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.NRBiConsumerEvent;

@Weave(type=MatchType.Interface)
public abstract class EventContextFactory {

//	public static EventContext create(FlowConstruct flow, ComponentLocation location) {
//		EventContext eventCtx = Weaver.callOriginal();
//		return eventCtx;
//	}
//
//	public static EventContext create(FlowConstruct flow, ComponentLocation location, String correlationId) {
//		EventContext eventCtx = Weaver.callOriginal();
//		return eventCtx;
//	}
//
//	public static EventContext create(String id, String serverId, ComponentLocation location,
//			FlowExceptionHandler exceptionHandler) {
//		EventContext eventCtx = Weaver.callOriginal();
//		return eventCtx;
//	}
//
//	public static EventContext create(String id, String serverId, ComponentLocation location, String correlationId,
//			FlowExceptionHandler exceptionHandler) {
//		EventContext eventCtx = Weaver.callOriginal();
//		return eventCtx;
//	}
//
//	public static EventContext create(FlowConstruct flow, ComponentLocation location, String correlationId,Optional<CompletableFuture<Void>> externalCompletion) {
//		EventContext eventCtx = Weaver.callOriginal();
//		if(BaseEventContext.class.isInstance(eventCtx)) {
//			NRBiConsumerEvent consumer = new NRBiConsumerEvent(NewRelic.getAgent().getTransaction().getToken());
//			BaseEventContext bEventCtx = (BaseEventContext)eventCtx;
//			bEventCtx.onComplete(consumer);
//			bEventCtx.onResponse(consumer);
//			bEventCtx.onTerminated(consumer);
//		}
//		return eventCtx;
//	}
//
//	public static EventContext create(FlowConstruct flow, FlowExceptionHandler exceptionHandler, ComponentLocation location,
//			String correlationId, Optional<CompletableFuture<Void>> externalCompletion) {
//		EventContext eventCtx = Weaver.callOriginal();
//		if(BaseEventContext.class.isInstance(eventCtx)) {
//			NRBiConsumerEvent consumer = new NRBiConsumerEvent(NewRelic.getAgent().getTransaction().getToken());
//			BaseEventContext bEventCtx = (BaseEventContext)eventCtx;
//			bEventCtx.onComplete(consumer);
//			bEventCtx.onResponse(consumer);
//			bEventCtx.onTerminated(consumer);
//		}
//		return eventCtx;
//	}
//
//	public static EventContext create(String id, String serverId, ComponentLocation location, String correlationId,
//			Optional<CompletableFuture<Void>> externalCompletion, FlowExceptionHandler exceptionHandler) {
//		EventContext eventCtx = Weaver.callOriginal();
//		if(BaseEventContext.class.isInstance(eventCtx)) {
//			NRBiConsumerEvent consumer = new NRBiConsumerEvent(NewRelic.getAgent().getTransaction().getToken());
//			BaseEventContext bEventCtx = (BaseEventContext)eventCtx;
//			bEventCtx.onComplete(consumer);
//			bEventCtx.onResponse(consumer);
//			bEventCtx.onTerminated(consumer);
//		}
//		return eventCtx;
//	}

}
