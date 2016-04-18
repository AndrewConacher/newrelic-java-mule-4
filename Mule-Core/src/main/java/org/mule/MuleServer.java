package org.mule;

import org.mule.api.MuleContext;
import org.mule.api.config.MuleConfiguration;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.ApplicationNamePriority;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public  abstract class MuleServer {

	public abstract MuleContext getMuleContext();

	@Trace(skipTransactionTrace=true)
	protected void init(String[] args) {
		Weaver.callOriginal();
		String systemName = null;
		MuleContext context = getMuleContext();
		if(context != null) {
			MuleConfiguration config = context.getConfiguration();
			if(config != null) {
				systemName = config.getSystemName();
			}
		}
		if(systemName == null) {
			systemName = "Unknown System";
		}
		String clusterId = context == null ? "Unknown Cluster" : context.getClusterId();
		AgentBridge.getAgent().getTransaction().setApplicationName(ApplicationNamePriority.CONTEXT_NAME, systemName + "-"+clusterId);
	}
}
