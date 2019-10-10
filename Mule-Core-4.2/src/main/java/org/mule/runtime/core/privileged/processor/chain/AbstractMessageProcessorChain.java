package org.mule.runtime.core.privileged.processor.chain;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.api.processor.Processor;
import org.mule.runtime.core.api.processor.strategy.ProcessingStrategy;
import org.reactivestreams.Publisher;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.NRFlux;

import reactor.core.publisher.Flux;
import reactor.util.context.Context;

@Weave(type=MatchType.BaseClass)
class AbstractMessageProcessorChain {
	
	@NewField
	protected String chainName = "Unknown";
	
	AbstractMessageProcessorChain(final String name, final Optional<ProcessingStrategy> processingStrategyOptional,final List<Processor> processors) {
		if(name != null && !name.isEmpty()) {
			chainName = name;
		}
	}
	
	@Trace(dispatcher=true)
	public Publisher<CoreEvent> apply(final Publisher<CoreEvent> publisher) {
		Publisher<CoreEvent> result = Weaver.callOriginal();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleProcessorChain",getClass().getSimpleName(),"apply",chainName});
		if(Flux.class.isInstance(result)) {
			Flux<CoreEvent> flux = (Flux<CoreEvent>)result;
			NRFlux wrapper = new NRFlux(flux);
			result = (Publisher<CoreEvent>)wrapper;
		}
		return result;
	}
	

	@Trace(dispatcher=true)
	public CoreEvent process(final CoreEvent event) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleProcessorChain",getClass().getSimpleName(),"process",chainName});
		CoreEvent retValue = Weaver.callOriginal();
		return retValue;
	}
	
	
	@Trace
	private Function<? super Publisher<CoreEvent>, ? extends Publisher<CoreEvent>> doOnNextOrErrorWithContext(final Consumer<Context> contextConsumer) {
		Function<? super Publisher<CoreEvent>, ? extends Publisher<CoreEvent>> f = Weaver.callOriginal();
		return f;
	}
}
