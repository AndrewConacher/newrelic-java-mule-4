package org.mule.runtime.core.internal.execution;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.api.execution.ExecutionCallback;
import org.mule.runtime.core.internal.event.MuleUtils;
import org.mule.runtime.module.extension.api.runtime.privileged.EventedExecutionContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class ExecutionInterceptor<T> {

	@SuppressWarnings("rawtypes")
	@Trace(dispatcher=true)
	public T execute(ExecutionCallback<T> callback, ExecutionContext executionContext) {
		Token token = null;
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","ExecutionInterceptor",getClass().getName(),"execute");
		if(EventedExecutionContext.class.isInstance(executionContext)) {
			EventedExecutionContext eeContext = (EventedExecutionContext)executionContext;
			CoreEvent coreEvent = eeContext.getEvent();
			token = MuleUtils.getToken(coreEvent);
		}
		if(token == null && callback.token != null) {
			token = callback.token;
		}
		if(token != null) {
			token.link();
		}
		return Weaver.callOriginal();
	}
}
