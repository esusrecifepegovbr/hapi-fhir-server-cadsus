package br.com.gointerop.hapi.fhir.translator;

import java.util.HashMap;

import org.hl7.fhir.valueset.Patient;

import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import br.gov.saude.esusab.valueset.AdministrativeGender;
import ca.uhn.fhir.rest.param.BaseParam;

public class TranslatorPatient extends Translator {
	private static final String FIELD_GENDER = "gender";

	@Override
	public HashMap<String, BaseParam> translate(HashMap<String, BaseParam> params) {
		HashMap<String, BaseParam> retVal = new HashMap<String, BaseParam>();

		for (String key : params.keySet()) {
			BaseParam value = params.get(key);
			Object translatedValue = null;
			
			if(value == null) continue;
			
			switch (key) {
			case FIELD_GENDER:
				translatedValue = translateGender(UtilBaseParam.toValue(value));
				break;
			}
			
			if(translatedValue != null) {
				retVal.put(key, UtilBaseParam.toBaseParam(translatedValue));
			} else {
				retVal.put(key, value);
			}
		}

		return retVal;
	}

	public Object translateGender(String value) {
		int retVal = AdministrativeGender.UNKNOWN;

		switch (value) {
		case Patient.male:
			retVal = AdministrativeGender.MALE;
			break;
		case Patient.female:
			retVal = AdministrativeGender.FEMALE;
			break;
		default:
			retVal = AdministrativeGender.UNKNOWN;
		}

		return retVal;
	}

}
