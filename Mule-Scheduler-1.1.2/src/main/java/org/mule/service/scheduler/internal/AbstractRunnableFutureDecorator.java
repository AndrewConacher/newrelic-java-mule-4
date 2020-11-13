package org.mule.service.scheduler.internal;

import java.util.concurrent.RunnableFuture;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
abstract class AbstractRunnableFutureDecorator<V> {
	
	@NewField
	protected Token token = null;

	protected AbstractRunnableFutureDecorator(Integer id, ClassLoader classLoader) {
		token = NewRelic.getAgent().getTransaction().getToken();
	}
	
	@Trace(async=true)
	protected void doRun(RunnableFuture<V> task) {
		String name = getName();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","RunnableFutureDecorator",name});
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}
	
	
	public abstract String getSchedulerName();
	
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
