package org.mule.api.transport;

import java.io.OutputStream;
import java.util.logging.Level;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.transaction.Transaction;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class MessageReceiver {

	  public abstract InboundEndpoint getEndpoint();

	  public abstract FlowConstruct getFlowConstruct();

	  public abstract EndpointURI getEndpointURI();

	  @Trace(dispatcher=true)
	  public MuleEvent routeMessage(MuleMessage paramMuleMessage) {
		  FlowConstruct flowConstruct = getFlowConstruct();
		  EndpointURI endpointURI = getEndpointURI();
		  AgentBridge.getAgent().getLogger().log(Level.FINE, "MessageReceiver.route ({0}) Endpoint URI: {1}", new Object[] {getClass().getName(),endpointURI.getUri().toASCIIString()});
		  AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MessageReceiver","routeMessage",getClass().getName(),flowConstruct.getName()});
		  return Weaver.callOriginal();
	  }

	  public MuleEvent routeMessage(MuleMessage paramMuleMessage, Transaction paramTransaction) {
		  FlowConstruct flowConstruct = getFlowConstruct();
		  EndpointURI endpointURI = getEndpointURI();
		  AgentBridge.getAgent().getLogger().log(Level.FINE, "MessageReceiver.route ({0}) Endpoint URI: {1}", new Object[] {getClass().getName(),endpointURI.getUri().toASCIIString()});
		  AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MessageReceiver","routeMessage",getClass().getName(),flowConstruct.getName()});
		  return Weaver.callOriginal();
	  }

	  public MuleEvent routeMessage(MuleMessage paramMuleMessage, Transaction paramTransaction, OutputStream paramOutputStream) {
		  FlowConstruct flowConstruct = getFlowConstruct();
		  EndpointURI endpointURI = getEndpointURI();
		  AgentBridge.getAgent().getLogger().log(Level.FINE, "MessageReceiver.route ({0}) Endpoint URI: {1}", new Object[] {getClass().getName(),endpointURI.getUri().toASCIIString()});
		  AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MessageReceiver","routeMessage",getClass().getName(),flowConstruct.getName()});
		  return Weaver.callOriginal();
	  }

}
