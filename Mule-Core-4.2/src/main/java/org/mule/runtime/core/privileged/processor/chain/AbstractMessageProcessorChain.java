package org.mule.runtime.core.privileged.processor.chain;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;

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
import com.newrelic.mule.core.NRWrapperFunction;

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
//		Exception e = new Exception("call to apply");
		
		Publisher<CoreEvent> result = Weaver.callOriginal();
//		NewRelic.getAgent().getLogger().log(Level.FINE, e, "call to {0}.apply({1}) returns {2}" , getClass(), publisher, result);
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
	
	
	@SuppressWarnings("unused")
	@Trace
	private Function<? super Publisher<CoreEvent>, ? extends Publisher<CoreEvent>> doOnNextOrErrorWithContext(final Consumer<Context> contextConsumer) {
		Function<? super Publisher<CoreEvent>, ? extends Publisher<CoreEvent>> f = Weaver.callOriginal();
		return f;
	}
}
