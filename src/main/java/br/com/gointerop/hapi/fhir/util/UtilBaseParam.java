package br.com.gointerop.hapi.fhir.util;

import java.util.Date;

import br.com.gointerop.hapi.fhir.translator.ITranslator;
import br.com.gointerop.hapi.fhir.translator.TranslatorFactory;
import br.com.gointerop.hapi.fhir.translator.TranslatorResource;
import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.NumberParam;
import ca.uhn.fhir.rest.param.StringParam;

public class UtilBaseParam {
	public static String toValue(BaseParam value) {
		String retVal = null;

		if (value instanceof NumberParam)
			retVal = ((NumberParam) value).getValue() + "";

		if (value instanceof StringParam)
			retVal = ((StringParam) value).getValue();

		if (value instanceof DateParam)
			retVal = UtilDate.toISOString(((DateParam) value).getValue());

		return retVal;
	}
	
	public static BaseParam toBaseParam(Object value) {
		BaseParam retVal = null;
		
		if (value instanceof String)
			retVal = new StringParam(value.toString());

		if (value instanceof Date)
			retVal = new DateParam(value.toString());

		if (value instanceof Integer)
			retVal = new NumberParam(value.toString());
		
		return retVal;
	}
	
	public static String toFilterValue(BaseParam value) {
		String retVal = null;

		if (value instanceof NumberParam)
			retVal = " = " + UtilBaseParam.toValue(value) + "";

		if (value instanceof StringParam)
			retVal = " LIKE '%" + UtilBaseParam.toValue(value) + "%'";

		if (value instanceof DateParam)
			retVal = " = TO_DATE('" + UtilBaseParam.toValue(value) + "', 'YYYY-MM-DD HH24:MI:SS')";

		return retVal;
	}
}
