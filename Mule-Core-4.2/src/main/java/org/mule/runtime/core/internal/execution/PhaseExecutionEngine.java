package org.mule.runtime.core.internal.execution;

import org.mule.runtime.core.api.source.MessageSource;
import org.mule.runtime.core.privileged.execution.MessageProcessContext;
import org.mule.runtime.core.privileged.execution.MessageProcessTemplate;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.Utils;

@Weave
public abstract class PhaseExecutionEngine {

	@Trace
	public void process(MessageProcessTemplate messageProcessTemplate, MessageProcessContext messageProcessContext) {
		MessageSource source = messageProcessContext.getMessageSource();
		String tmp = Utils.getMessageLocation(source);
		String location = tmp != null ? tmp : "Unknown";
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","PhaseExecutionEngine","process",location);
		Weaver.callOriginal();
	}
	
	@Weave
	public static class InternalPhaseExecutionEngine {

		private final MessageProcessContext messageProcessContext = Weaver.callOriginal();
		
		public InternalPhaseExecutionEngine(final MessageProcessTemplate messageProcessTemplate,
				final MessageProcessContext messageProcessContext) {
			
		}
		
		@NewField
		private Segment segment = null;
		
		public void process() {
			if(messageProcessContext != null) {
				MessageSource source = messageProcessContext.getMessageSource();
				String location = Utils.getMessageLocation(source);
				if(location != null) {
					segment = NewRelic.getAgent().getTransaction().startSegment("Phase-"+location);
				}
			}
			Weaver.callOriginal();
		}

		@SuppressWarnings("unused")
		private void processEndPhase() {
			segment.end();
			Weaver.callOriginal();
		}
	}
}
