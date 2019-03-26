package org.mule.runtime.api.service;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveIntoAllMethods;

@Weave(type=MatchType.Interface)
public abstract class Service {

	public abstract String getName();
	
	@WeaveIntoAllMethods
	@Trace(dispatcher=true)
	public static void instrumentation() {
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		
		StackTraceElement first = traces[1];
		String name = null;
		if(name == null || name.isEmpty()) {
			String classname = first.getClassName();
			name = classname;
		}
		String methodName = first.getMethodName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Mule","Service",name,methodName});

	}
}
