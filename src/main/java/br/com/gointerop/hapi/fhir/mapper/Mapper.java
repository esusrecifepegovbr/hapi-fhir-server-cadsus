package br.com.gointerop.hapi.fhir.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;

import ca.uhn.fhir.rest.param.BaseParam;

public abstract class Mapper implements IMapper {
	public HashMap<String, BaseParam> map(HashMap<String, BaseParam> params) {
		HashMap<String, BaseParam> retVal = new HashMap<String, BaseParam>();
		
		for (String key : params.keySet()) {
			BaseParam value = params.get(key);
			
			for (Field field : this.getClass().getFields()) {
				boolean isPropertyEqual = false;
	
				try {
					String fieldName = field.getName();
					String fieldValue = field.get(null).toString();
					isPropertyEqual = fieldName.equalsIgnoreCase(key);
					
					if (isPropertyEqual && fieldValue != null && value != null)
						retVal.put(fieldValue, value);
				} catch (Exception e) {}
			}
		}
		return retVal;
	}
}
