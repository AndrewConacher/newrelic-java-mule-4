package org.mule.runtime.core.internal.event;

import org.mule.runtime.core.internal.message.InternalEvent;
import org.mule.runtime.core.privileged.event.BaseEventContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.BaseClass)
abstract class BaseEventDecorator {

	
	@NewField
	public Token token = null;
	
	public BaseEventDecorator(final InternalEvent event) {
//		BaseEventContext ctx = event.getContext();
//		if(BaseEventContext.class.isInstance(ctx)) {
//			BaseEventContext bCtx = (BaseEventContext)ctx;
//			if(bCtx.token != null) {
//				token = ((BaseEventContext)ctx).token;
//			} else {
//				token = NewRelic.getAgent().getTransaction().getToken();
//				bCtx.token = token;
//			}
//		}
	}
}
