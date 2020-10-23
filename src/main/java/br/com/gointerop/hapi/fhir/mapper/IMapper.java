package br.com.gointerop.hapi.fhir.mapper;

import java.util.HashMap;

import ca.uhn.fhir.rest.param.BaseParam;

public interface IMapper {
	public String getTableName();
	public String getPrimaryKey();
	public HashMap<String, BaseParam> map(HashMap<String, BaseParam> params);
}
