package org.mule.runtime.core.internal.execution;

import java.util.logging.Level;

import org.mule.runtime.core.privileged.execution.MessageProcessContext;
import org.mule.runtime.core.privileged.execution.MessageProcessTemplate;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class PhaseExecutionEngine {

	@Trace(dispatcher=true)
	public void process(MessageProcessTemplate messageProcessTemplate, MessageProcessContext messageProcessContext) {
		Exception e = new Exception("In PhaseExecutionEngine");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "Called PhaseExecutionEngine.process({0},{1})",messageProcessTemplate,messageProcessContext );
		Weaver.callOriginal();
	}
}
