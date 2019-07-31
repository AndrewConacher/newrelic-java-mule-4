package reactor.core.publisher;

import java.util.function.LongConsumer;
import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import reactor.core.Disposable;

@Weave(type=MatchType.Interface)
public abstract class FluxSink<T> {

	@Trace(dispatcher=true)
	public void complete() {
		Exception e = new Exception("FluxSink.complete");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "Enter {0}.complete()",getClass());
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void error(final Throwable p0) {
		Exception e = new Exception("FluxSink.error");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "Enter {0}.error()",getClass());
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public FluxSink<T> next(final T p0) {
		Exception e = new Exception("FluxSink.next");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "Enter {0}.next({1})",getClass(),p0);
		return Weaver.callOriginal();
	}



	@Trace(dispatcher=true)
	public FluxSink<T> onRequest(final LongConsumer p0) {
		Exception e = new Exception("FluxSink.onRequest");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "Enter {0}.onRequest({1})",getClass(),p0);
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public FluxSink<T> onCancel(final Disposable p0) {
		Exception e = new Exception("FluxSink.onCancel");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "Enter {0}.onCancel({1})",getClass(),p0);
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public FluxSink<T> onDispose(final Disposable p0) {
		Exception e = new Exception("FluxSink.onDispose");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "Enter {0}.onDispose({1})",getClass(),p0);
		return Weaver.callOriginal();
	}
}
