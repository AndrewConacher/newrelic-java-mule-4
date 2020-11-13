package org.mule.runtime.module.extension.internal.runtime.source;

import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.SourceCallbackContext;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
abstract class DefaultSourceCallback<T, A> {

	@Trace(dispatcher=true)
	public void handle(Result<T, A> result, SourceCallbackContext context) {
		Weaver.callOriginal();
	}
}
