package org.mule.runtime.core.api.execution;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class ExecutionTemplate<T> {

	@Trace(dispatcher=true)
	public T execute(ExecutionCallback<T> callback) {
		return Weaver.callOriginal();
	}
}
