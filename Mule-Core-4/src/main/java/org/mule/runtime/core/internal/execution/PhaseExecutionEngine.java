package org.mule.runtime.core.internal.execution;

import org.mule.runtime.core.privileged.execution.MessageProcessContext;
import org.mule.runtime.core.privileged.execution.MessageProcessTemplate;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class PhaseExecutionEngine {

	@Trace(dispatcher=true)
	public void process(MessageProcessTemplate messageProcessTemplate, MessageProcessContext messageProcessContext) {
		Weaver.callOriginal();
	}
}
