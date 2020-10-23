package org.hl7.fhir.helper;

import org.hl7.fhir.r4.model.Meta;

public class HelperMeta {
	public static Meta create(String url) {
		Meta retVal = new Meta();
		
		retVal.addProfile(url);
		
		return retVal;
	}
}
