package org.mule.service.scheduler.internal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRRunnableFuture<V> implements RunnableFuture<V> {
	
	private Token token = null;
	private RunnableFuture<V> delegate = null;

	public NRRunnableFuture(Token token, RunnableFuture<V> delegate) {
		super();
		this.token = token;
		this.delegate = delegate;
	}

	@Override
	@Trace(async=true)
	public boolean cancel(boolean mayInterruptIfRunning) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		return delegate.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return delegate.isCancelled();
	}

	@Override
	public boolean isDone() {
		return delegate.isDone();
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		return delegate.get();
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return delegate.get(timeout, unit);
	}

	@Override
	@Trace(async=true)
	public void run() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		delegate.run();
	}

}
