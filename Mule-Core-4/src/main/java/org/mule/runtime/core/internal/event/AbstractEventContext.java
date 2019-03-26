package org.mule.runtime.core.internal.event;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.api.exception.FlowExceptionHandler;
import org.mule.runtime.core.privileged.event.BaseEventContext;
import org.reactivestreams.Publisher;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.MuleUtils;
import com.newrelic.mule.core.NRBiConsumerEvent;

@Weave(type=MatchType.BaseClass)
abstract class AbstractEventContext  implements BaseEventContext {

	@NewField
	public Token token = null;

	public AbstractEventContext() {

	}

	public AbstractEventContext(FlowExceptionHandler exceptionHandler) {
	}

	public AbstractEventContext(FlowExceptionHandler exceptionHandler, int depthLevel,Optional<CompletableFuture<Void>> externalCompletion) {
		Exception e = new Exception("Constructing AbstractEventContext" );
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "AbstractEventContext.<init>({0},{1},{2})",exceptionHandler,depthLevel,externalCompletion);
		token = NewRelic.getAgent().getTransaction().getToken();
		NRBiConsumerEvent nrConsumer = new NRBiConsumerEvent(token);
		onComplete(nrConsumer);
		onTerminated(nrConsumer);
		onResponse(nrConsumer);
	}

	@Trace(async=true)
	public void success() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	public void success(CoreEvent event) {
		if(MuleUtils.hasToken(event)) {
			MuleUtils.getToken(event).expire();
			MuleUtils.removeToken(event);
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	public Publisher<Void> error(Throwable throwable) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.noticeError(throwable);
		return Weaver.callOriginal();
	}

	private boolean isResponseDone() {
		return Weaver.callOriginal();
	}
}
