package org.mule.api.client;

import java.util.Map;
import java.util.logging.Level;

import org.mule.MessageExchangePattern;
import org.mule.api.MuleMessage;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class MuleClient {

	@Trace(dispatcher=true)
	public void dispatch(String paramString, Object paramObject, Map<String, Object> paramMap) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "MuleClient {0} - dispatch({1},{2},{3})", new Object[] {getClass().getName(),paramString,paramObject,paramMap});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleClient",getClass().getName(),"dispatch"});
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void dispatch(String paramString, MuleMessage paramMuleMessage) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "MuleClient {0} - dispatch({1},{2})", new Object[] {getClass().getName(),paramString,paramMuleMessage});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleClient",getClass().getName(),"dispatch"});
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public MuleMessage send(String paramString, Object paramObject, Map<String, Object> paramMap) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "MuleClient {0} - send({1},{2},{3})", new Object[] {getClass().getName(),paramString,paramObject,paramMap});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleClient",getClass().getName(),"send"});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public MuleMessage send(String paramString, MuleMessage paramMuleMessage) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "MuleClient {0} - send({1},{2})", new Object[] {getClass().getName(),paramString,paramMuleMessage});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleClient",getClass().getName(),"send"});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public MuleMessage send(String paramString, Object paramObject, Map<String, Object> paramMap, long paramLong) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "MuleClient {0} - send({1},{2},{3},{4})", new Object[] {getClass().getName(),paramString,paramObject,paramMap,paramLong});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleClient",getClass().getName(),"send"});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public MuleMessage send(String paramString, MuleMessage paramMuleMessage, long paramLong) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "MuleClient {0} - send({1},{2},{3})", new Object[] {getClass().getName(),paramString,paramMuleMessage,paramLong});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleClient",getClass().getName(),"send"});
		return Weaver.callOriginal();
	}

	public MuleMessage request(String paramString, long paramLong) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "MuleClient {0} - request({1},{2})", new Object[] {getClass().getName(),paramString,paramLong});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleClient",getClass().getName(),"request"});
		return Weaver.callOriginal();
	}

	public MuleMessage process(String paramString, MessageExchangePattern paramMessageExchangePattern, Object paramObject, Map<String, Object> paramMap) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "MuleClient {0} - process({1},{2},{3},{4])", new Object[] {getClass().getName(),paramString,paramMessageExchangePattern,paramObject,paramMap});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleClient",getClass().getName(),"process"});
		return Weaver.callOriginal();
	}

	public MuleMessage process(String paramString, MessageExchangePattern paramMessageExchangePattern, MuleMessage paramMuleMessage) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "MuleClient {0} - process({1},{2},{3})", new Object[] {getClass().getName(),paramString,paramMessageExchangePattern,paramMuleMessage});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MuleClient",getClass().getName(),"process"});
		return Weaver.callOriginal();
	}

}
