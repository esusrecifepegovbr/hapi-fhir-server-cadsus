package br.com.gointerop.hapi.fhir.controller;

import java.util.HashMap;
import java.util.List;

import ca.uhn.fhir.rest.param.BaseParam;

public interface IController<T> {

	T readById(Long id);

	List<T> search(HashMap<String, BaseParam> params);
}
