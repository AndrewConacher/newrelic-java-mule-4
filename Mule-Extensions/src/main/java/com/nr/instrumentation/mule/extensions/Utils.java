package com.nr.instrumentation.mule.extensions;

import java.util.ArrayList;
import java.util.List;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.agent.config.AgentConfig;
import com.newrelic.api.agent.ApplicationNamePriority;
import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;

public class Utils {
	
	private static List<String> appNames = new ArrayList<String>();
	private static Boolean autoNamingEnabled = null;
	
	
	public static void addAppName(String appName) {
		if(autoNamingEnabled == null) {
			Config config = NewRelic.getAgent().getConfig();
			if(config != null && config instanceof AgentConfig) {
				AgentConfig aConfig = (AgentConfig)config;
				autoNamingEnabled = aConfig.isAutoAppNamingEnabled();
			}
		}
		if(appName == null || autoNamingEnabled == null || !autoNamingEnabled || appNames.contains(appName)) return;
		
		appNames.add(appName);
		AgentBridge.getAgent().getTransaction(false).setApplicationName(ApplicationNamePriority.REQUEST_ATTRIBUTE, appName);
		
	}

}
