package org.mule.runtime.module.extension.internal.runtime.execution;

import java.lang.reflect.Method;
import java.util.List;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;
import org.mule.runtime.extension.api.runtime.operation.ExecutionContext;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ReflectiveMethodComponentExecutor<M extends ComponentModel> {
	
	public ReflectiveMethodComponentExecutor(List<ParameterGroupModel> groups, Method method, Object componentInstance) {
		
	}

	@Trace
	public Object execute(ExecutionContext<M> executionContext) {
		Object value = Weaver.callOriginal();
		
		return value;
	}
	
}
