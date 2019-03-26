package org.reactivestreams;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.mule.reactor.NRSubscriber;

@Weave(type=MatchType.Interface)
public abstract class Publisher<T> {

	
	public void subscribe(Subscriber<? super T> s) {
		
		NRSubscriber<? super T> wrapper = new NRSubscriber<T>(s);
		s = wrapper;
		Weaver.callOriginal();
	}
}
