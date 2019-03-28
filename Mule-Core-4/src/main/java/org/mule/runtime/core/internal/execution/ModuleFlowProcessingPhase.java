package org.mule.runtime.core.internal.execution;

import java.util.function.Consumer;

import org.mule.runtime.core.api.construct.FlowConstruct;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.policy.PolicyManager;
import org.mule.runtime.core.privileged.execution.MessageProcessContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.MuleUtils;
import com.newrelic.mule.core.NREventConsumer;

@Weave
public abstract class ModuleFlowProcessingPhase {
	
	public ModuleFlowProcessingPhase(PolicyManager policyManager) {
		
	}
	
	@Weave
	static final class PhaseContext {
		
	}
	
	@Weave
	private static class FlowProcessor {

		@Trace
		public CoreEvent process(final CoreEvent event) {
			
			if(MuleUtils.hasToken(event)) {
				Token token = MuleUtils.getToken(event);
				if(token != null) {
					token.linkAndExpire();
					MuleUtils.removeToken(event);
				}
			}
			CoreEvent returned = Weaver.callOriginal();
			
			if(!MuleUtils.hasToken(returned)) {
				Token token = NewRelic.getAgent().getTransaction().getToken();
				if(!MuleUtils.addToken(returned, token)) {
					token.expire();
					token = null;
				}
			}
			return returned;
		}
		
	}

	
	@SuppressWarnings("unused")
	private Consumer<CoreEvent> onMessageReceived(ModuleFlowProcessingPhaseTemplate template,MessageProcessContext messageProcessContext, FlowConstruct flowConstruct) {
		Consumer<CoreEvent> consumer = Weaver.callOriginal();
		NREventConsumer nrConsumer = new NREventConsumer();
		
		return nrConsumer.andThen(consumer);
	}
	
}
