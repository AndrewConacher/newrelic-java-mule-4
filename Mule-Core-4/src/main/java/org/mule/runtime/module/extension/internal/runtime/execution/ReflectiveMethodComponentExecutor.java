package org.mule.runtime.module.extension.internal.runtime.execution;

import java.lang.reflect.Method;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.extension.api.runtime.operation.ExecutionContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ReflectiveMethodComponentExecutor<M extends ComponentModel> {
	
	private final Method method = Weaver.callOriginal();
	private final Object componentInstance = Weaver.callOriginal();
	
	@Trace(async=true)
	public Object execute(ExecutionContext<M> executionContext) {
		String declaringClass = method.getDeclaringClass().getSimpleName();
		String methodName = method.getName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","ReflectiveMethodComponentExecutor","excecute",componentInstance.getClass().getName(),declaringClass,methodName});
		Object value = Weaver.callOriginal();
		
		return value;
	}
	
}
