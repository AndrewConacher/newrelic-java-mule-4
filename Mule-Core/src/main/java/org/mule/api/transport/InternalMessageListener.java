package org.mule.api.transport;

import java.io.OutputStream;

import org.mule.api.MuleMessage;
import org.mule.api.transaction.Transaction;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class InternalMessageListener {

	@Trace(dispatcher=true)
	  public MuleMessage onMessage(MuleMessage paramMuleMessage, Transaction paramTransaction, boolean paramBoolean, OutputStream paramOutputStream) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","InternalMessageListener",getClass().getName(),"onMessage"});
		return Weaver.callOriginal();
	}

}
