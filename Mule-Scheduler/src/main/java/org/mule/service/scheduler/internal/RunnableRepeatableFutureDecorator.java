package org.mule.service.scheduler.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import java.util.function.Consumer;
import java.util.concurrent.RunnableFuture;
import java.util.function.Supplier;

@Weave
abstract class RunnableRepeatableFutureDecorator<V> extends AbstractRunnableFutureDecorator<V> {

	RunnableRepeatableFutureDecorator(Supplier<RunnableFuture<V>> taskSupplier,
            Consumer<RunnableRepeatableFutureDecorator<V>> wrapUpCallback,
            ClassLoader classLoader, DefaultScheduler scheduler, String taskAsString, Integer id) {
		super(id);
	}
	
	@Trace(async=true)
	public boolean cancel(boolean mayInterruptIfRunning) {
		String name = getName();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","RunnableFutureDecorator","cancel",name});
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		return Weaver.callOriginal();
	}

	private String getName() {
		String tmp = toString();
		String scheduler = getSchedulerName();
		if(tmp.startsWith(scheduler)) {
			int length = scheduler.length();
			return tmp.substring(length+3);
		}
		return tmp;
	}

}
