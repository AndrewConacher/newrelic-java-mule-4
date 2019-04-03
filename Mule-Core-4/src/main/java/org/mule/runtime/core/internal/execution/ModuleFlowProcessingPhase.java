package org.mule.runtime.core.internal.execution;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.core.api.construct.FlowConstruct;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.policy.PolicyManager;
import org.mule.runtime.core.privileged.execution.MessageProcessContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.MuleUtils;
import com.newrelic.mule.core.NRBiConsumer;
import com.newrelic.mule.core.NREventConsumer;

@Weave
public abstract class ModuleFlowProcessingPhase {
	
	public ModuleFlowProcessingPhase(PolicyManager policyManager) {
		
	}
	
	@Weave
	static final class PhaseContext {
		
	}
	

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private CoreEvent createEvent(ModuleFlowProcessingPhaseTemplate template,
			ComponentLocation sourceLocation, CompletableFuture responseCompletion,
			 FlowConstruct flowConstruct) {
		
		NRBiConsumer<?,?> nrConsumer = new NRBiConsumer(NewRelic.getAgent().getTransaction().getToken(),flowConstruct.getName() != null ? flowConstruct.getName() : null);
		responseCompletion = responseCompletion.whenComplete(nrConsumer);
		CoreEvent event = Weaver.callOriginal();
		String corrId = event.getCorrelationId();
		if(!MuleUtils.hasToken(corrId)) {
			Token token = NewRelic.getAgent().getTransaction().getToken();
			MuleUtils.addToken(corrId, token);
		}
		return event;
	}
	
	@SuppressWarnings("unused")
	private Consumer<CoreEvent> onMessageReceived(ModuleFlowProcessingPhaseTemplate template,MessageProcessContext messageProcessContext, FlowConstruct flowConstruct) {
		Consumer<CoreEvent> consumer = Weaver.callOriginal();
		NREventConsumer nrConsumer = new NREventConsumer();
		
		return nrConsumer.andThen(consumer);
	}
	
}
