package org.mule.runtime.core.privileged.processor.chain;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;
import org.reactivestreams.Publisher;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.NRFunction;

import reactor.util.context.Context;

@Weave(type=MatchType.BaseClass)
class AbstractMessageProcessorChain {

	@Trace(dispatcher=true)
	public CoreEvent process(final CoreEvent event) {
		CoreEvent retValue = Weaver.callOriginal();
		NewRelic.getAgent().getLogger().log(Level.FINE, "AbstractMessageProcessorChain - {0}.process({1}) is returning {2}", getClass(),event,retValue);
		return retValue;
	}
	
	@Trace(dispatcher=true)
	private Function<? super Publisher<CoreEvent>, ? extends Publisher<CoreEvent>> doOnNextOrErrorWithContext(Consumer<Context> contextConsumer) {
		Function<? super Publisher<CoreEvent>, ? extends Publisher<CoreEvent>> f = Weaver.callOriginal();
		NRFunction<? super Publisher<CoreEvent>, ? extends Publisher<CoreEvent>> wrapper = new NRFunction(f);
		return wrapper;
	}
}
