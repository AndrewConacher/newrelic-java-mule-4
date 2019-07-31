package reactor.core.publisher;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.mule.reactor.NRCoreSubscriber;

import reactor.core.CoreSubscriber;

@Weave
abstract class FluxPeekFuseable<T> {

	@Trace(dispatcher=true)
	public void subscribe(CoreSubscriber<? super T> actual) {
		NRCoreSubscriber<? super T> nrCore = (NRCoreSubscriber<? super T>)(new NRCoreSubscriber(actual));
		actual = nrCore;
		Weaver.callOriginal();
	}
}
