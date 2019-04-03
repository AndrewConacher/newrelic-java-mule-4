package org.mule.runtime.core.internal.event;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

@Weave(type=MatchType.BaseClass)
abstract class AbstractEventContext  implements BaseEventContext {
	
	@NewField
	public Token token = null;

	public AbstractEventContext() {

	}

	public AbstractEventContext(FlowExceptionHandler exceptionHandler) {
	}

	public AbstractEventContext(FlowExceptionHandler exceptionHandler, int depthLevel,Optional<CompletableFuture<Void>> externalCompletion) {
		token = NewRelic.getAgent().getTransaction().getToken();
	}

	@Trace(async=true)
	public void success() {
		String contextCorrId = getCorrelationId();
		if(MuleUtils.hasToken(contextCorrId)) {
			Token token2 = MuleUtils.removeToken(contextCorrId);
			token2.linkAndExpire();
			token2 = null;
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	public void success(CoreEvent event) {
		
		String corrId = event.getCorrelationId();
		String contextCorrId = getCorrelationId();
		
		if(MuleUtils.hasToken(corrId)) {
			MuleUtils.getToken(corrId).expire();
			MuleUtils.removeToken(corrId);
		} else if(MuleUtils.hasToken(contextCorrId)) {
			MuleUtils.getToken(contextCorrId).expire();
			MuleUtils.removeToken(contextCorrId);
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	public Publisher<Void> error(Throwable throwable) {
		String contextCorrId = getCorrelationId();
		if(MuleUtils.hasToken(contextCorrId)) {
			Token token2 = MuleUtils.removeToken(contextCorrId);
			token2.linkAndExpire();
			token2 = null;
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.noticeError(throwable);
		return Weaver.callOriginal();
	}

}
