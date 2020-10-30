package br.com.gointerop.hapi.fhir.parser;

public interface IParser<T> {
	public T parse(String content) throws Exception;
}
