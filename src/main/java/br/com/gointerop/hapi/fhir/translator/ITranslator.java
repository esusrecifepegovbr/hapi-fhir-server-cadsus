package br.com.gointerop.hapi.fhir.translator;

import java.util.HashMap;

import ca.uhn.fhir.rest.param.BaseParam;

public interface ITranslator {
	public HashMap<String, BaseParam> translate(HashMap<String, BaseParam> params);
}
