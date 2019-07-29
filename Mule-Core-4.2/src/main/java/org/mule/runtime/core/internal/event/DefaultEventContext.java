package org.mule.runtime.core.internal.event;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.core.api.construct.FlowConstruct;
import org.mule.runtime.core.api.exception.FlowExceptionHandler;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.mule.core.MuleUtils;

@Weave
public abstract class DefaultEventContext extends AbstractEventContext {

	public DefaultEventContext(final String id, final String serverId, final ComponentLocation location,
			final String correlationId, final Optional<CompletableFuture<Void>> externalCompletion,
			final FlowExceptionHandler exceptionHandler) {
		super(exceptionHandler, 0, (Optional) externalCompletion);
		MuleUtils.addToken(correlationId, token);
		
	}
	
	public DefaultEventContext(final FlowConstruct flow, final FlowExceptionHandler exceptionHandler,
			final ComponentLocation location, final String correlationId,
			final Optional<CompletableFuture<Void>> externalCompletion) {
		super(exceptionHandler, 0, (Optional) externalCompletion);
		MuleUtils.addToken(correlationId, token);
	}
}
