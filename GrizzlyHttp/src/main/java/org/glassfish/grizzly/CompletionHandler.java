package org.glassfish.grizzly;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class CompletionHandler<E> {
	
	@WeaveAllConstructors
	public CompletionHandler() {
		token = NewRelic.getAgent().getTransaction().getToken();
	}
	
	@NewField
	public Token token = null;

	@Trace(async=true)
    public void cancelled() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
    	Weaver.callOriginal();
    }

	@Trace(async=true)
    public void failed(Throwable throwable) {
		NewRelic.noticeError(throwable);
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
    	Weaver.callOriginal();
    }

	@Trace(async=true)
    public void completed(E result) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		
    	Weaver.callOriginal();
    }

	@Trace(async=true)
    public void updated(E result) {
		if(token != null) {
			token.link();
		}
    	Weaver.callOriginal();
    }

}
