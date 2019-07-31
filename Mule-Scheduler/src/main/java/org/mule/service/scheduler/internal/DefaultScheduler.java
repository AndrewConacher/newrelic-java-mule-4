package org.mule.service.scheduler.internal;

import java.util.concurrent.RunnableFuture;
import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class DefaultScheduler {

	public abstract String getName();
	
	@Trace(excludeFromTransactionTrace=true)
	private <T> RunnableFuture<T> newDecoratedTaskFor(final RunnableFuture<T> newTaskFor, final Class<?> taskClass) {
		Exception e = new Exception("creating NRRunnableFuture");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "called newDecoratedTaskFor({0},{1})", newTaskFor, taskClass);
		RunnableFuture<T> f = Weaver.callOriginal();
		Token token = NewRelic.getAgent().getTransaction().getToken();
		NRRunnableFuture<T> wrapped = new NRRunnableFuture<T>(token, f);
		return wrapped;
	}
}
