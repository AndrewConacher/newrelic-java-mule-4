package org.mule.runtime.core.internal.execution;

import org.mule.runtime.core.api.source.MessageSource;
import org.mule.runtime.core.privileged.execution.MessageProcessContext;
import org.mule.runtime.core.privileged.execution.MessageProcessTemplate;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.Utils;

@Weave(type=MatchType.Interface)
public abstract class MessageProcessPhase<Template extends MessageProcessTemplate> {

	@Trace
	public void runPhase(Template messageProcessTemplate, MessageProcessContext messageProcessContext,PhaseResultNotifier phaseResultNotifier) {
		if(messageProcessContext != null) {
			MessageSource msgSource = messageProcessContext.getMessageSource();
			String locationName = Utils.getMessageLocation(msgSource);
			if(locationName != null) {
				NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MessageProcessPhase",getClass().getSimpleName(),"runPhase",locationName});
			}


		}

		Weaver.callOriginal();
	}
}
