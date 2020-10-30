package org.hl7.fhir.helper;

import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;

public class HelperIdentifier {
	public static Identifier create(String system, String code, String value) {
		Identifier retVal = new Identifier();
		
		retVal.setUse(IdentifierUse.OFFICIAL);
		retVal.setType(HelperCodeableConcept.create(system, code));
		retVal.setValue(value);
		
		return retVal;
	}
}
