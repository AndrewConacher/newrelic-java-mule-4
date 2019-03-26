package com.nr.instrumentation.mule.httpclient;

import java.util.function.BiConsumer;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

public class NRBiConsumer implements BiConsumer<HttpResponse, Throwable> {

	@Override
	public void accept(HttpResponse t, Throwable u) {
		
	}

}
