package org.mule.runtime.core.privileged.processor.chain;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.MuleUtils;

@Weave
class AbstractMessageProcessorChain$1 {

	@Trace(async=true)
	public void onNext(final CoreEvent event) {
		if(MuleUtils.hasToken(event)) {
			MuleUtils.getToken(event).link();
		}
		Weaver.callOriginal();
	}
}
