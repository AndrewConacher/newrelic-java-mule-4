package org.mule.runtime.core.internal.processor.strategy;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.NREventConsumer;

import org.mule.runtime.core.api.event.CoreEvent;
import java.util.function.Consumer;

@Weave(type=MatchType.BaseClass)
public class AbstractProcessingStrategy {

	protected Consumer<CoreEvent> createOnEventConsumer() {
		Consumer<CoreEvent> consumer = Weaver.callOriginal();
		if(NREventConsumer.class.isInstance(consumer)) {
			return consumer;
		} else {
			NREventConsumer wrapper = new NREventConsumer();
			return consumer.andThen(wrapper);
		}
	}
}
