package org.mule.runtime.module.extension.internal.runtime.source;

import org.mule.runtime.core.api.MuleContext;
import org.mule.runtime.extension.api.runtime.config.ConfigurationInstance;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.SourceCallbackContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.mule.extensions.Utils;

@SuppressWarnings("deprecation")
@Weave
abstract class DefaultSourceCallback<T, A> {

	private MuleContext muleContext = Weaver.callOriginal();

	@Trace(dispatcher=true)
	public void handle(Result<T, A> result, SourceCallbackContext context) {
	
		Utils.addAppName(getOwningSourceName());
		NewRelic.addCustomParameter("Owning-Source-Name", getOwningSourceName() != null ? getOwningSourceName() : "Unnamed");
		NewRelic.addCustomParameter("Owning-Extension-Name", getOwningExtensionName() != null ? getOwningExtensionName() : "Unnamed");
		NewRelic.addCustomParameter("MuleContext-ID", muleContext.getId());
		NewRelic.addCustomParameter("Configuration-Instance-Name", getConfigurationInstance().getName());
		Weaver.callOriginal();
	}
	
	  public abstract String getOwningSourceName();

	  public abstract String getOwningExtensionName();
	  
	  public abstract ConfigurationInstance getConfigurationInstance();
}
