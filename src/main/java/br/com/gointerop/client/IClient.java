package br.com.gointerop.client;

import br.com.gointerop.client.request.Request;

public interface IClient<T> {
	public T send(Request request) throws Exception;
}
