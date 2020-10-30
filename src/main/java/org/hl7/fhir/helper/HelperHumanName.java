package org.hl7.fhir.helper;

import org.hl7.fhir.r4.model.HumanName;

public class HelperHumanName {
	public static HumanName create(String text) {
		HumanName retVal = new HumanName();
		
		retVal.setText(text);
		
		return retVal;
	}
}
