package org.mule.service.scheduler.internal;

import java.util.concurrent.RunnableFuture;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
abstract class RunnableFutureDecorator<V> extends AbstractRunnableFutureDecorator<V> {

	RunnableFutureDecorator(RunnableFuture<V> task, ClassLoader classLoader, DefaultScheduler scheduler, String taskAsString, Integer id){
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
