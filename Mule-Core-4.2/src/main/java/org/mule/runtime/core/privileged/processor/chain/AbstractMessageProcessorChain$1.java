package org.mule.runtime.core.privileged.processor.chain;

import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.MuleUtils;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
abstract class AbstractMessageProcessorChain$1 {
	
	@Trace(async=true)
	public void onNext(final CoreEvent event) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MessageProcessorChain","onNext"});
		Token token = MuleUtils.getToken(event);
		if(token != null) {
			token.link();
		} else {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Failed to get token inAbstractMessageProcessorChain$1.onNext({0}",event);
		}
		Weaver.callOriginal();
	}
}
