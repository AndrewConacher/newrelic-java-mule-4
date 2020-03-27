package org.mule.runtime.module.extension.internal.runtime.execution;

import java.lang.reflect.Method;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.MuleUtils;
import org.mule.runtime.extension.api.runtime.operation.ExecutionContext;
import org.mule.runtime.module.extension.api.runtime.privileged.EventedExecutionContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ReflectiveMethodComponentExecutor<M extends ComponentModel> {
	
	private final Method method = Weaver.callOriginal();
	private final Object componentInstance = Weaver.callOriginal();
	
	@Trace(async=true)
	public Object execute(ExecutionContext<M> executionContext) {
		if(EventedExecutionContext.class.isInstance(executionContext)) {
			EventedExecutionContext<M> eeContext = (EventedExecutionContext<M>)executionContext;
			CoreEvent coreEvent = eeContext.getEvent();
			Token token = MuleUtils.getToken(coreEvent);
			if(token != null) {
				token.link();
			}
		}
		String declaringClass = method.getDeclaringClass().getSimpleName();
		String methodName = method.getName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","ReflectiveMethodComponentExecutor","excecute",componentInstance.getClass().getName(),declaringClass,methodName});
		Object value = Weaver.callOriginal();
		
		return value;
	}
	
}
