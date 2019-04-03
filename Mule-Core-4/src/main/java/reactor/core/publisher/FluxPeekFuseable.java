package reactor.core.publisher;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.mule.reactor.NRCoreSubscriber;

import reactor.core.CoreSubscriber;

@Weave
abstract class FluxPeekFuseable<T> {

	@SuppressWarnings("unchecked")
	public void subscribe(CoreSubscriber<? super T> actual) {
		@SuppressWarnings("rawtypes")
		NRCoreSubscriber<? super T> nrCore = new NRCoreSubscriber(actual);
		
		actual = nrCore;
		Weaver.callOriginal();
	}
}
