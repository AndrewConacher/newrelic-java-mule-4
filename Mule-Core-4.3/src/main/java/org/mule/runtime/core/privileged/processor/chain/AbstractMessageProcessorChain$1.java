package org.mule.runtime.core.privileged.processor.chain;

import org.mule.runtime.core.api.event.CoreEvent;
import org.reactivestreams.Subscription;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import reactor.util.context.Context;

@Weave
class AbstractMessageProcessorChain$1 {
	
	@Trace(excludeFromTransactionTrace=true)
	public void onNext(final CoreEvent event) {
		Weaver.callOriginal();
	}

	@Trace(async=true,excludeFromTransactionTrace=true)
	public void onError(final Throwable throwable) {
	
		Weaver.callOriginal();
	}

	@Trace(async=true,excludeFromTransactionTrace=true)
	public void onComplete() {
		Weaver.callOriginal();
	}

	public Context currentContext() {
		return Weaver.callOriginal();
	}

	public void onSubscribe(final Subscription s) {
		Weaver.callOriginal();
	}
	
}
