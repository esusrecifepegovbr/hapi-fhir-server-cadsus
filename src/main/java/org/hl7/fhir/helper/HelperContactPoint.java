package org.hl7.fhir.helper;

import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;

public class HelperContactPoint {
	public static ContactPoint create(String telefone) {
		ContactPoint retVal = new ContactPoint();
		
		retVal.setUse(ContactPointUse.MOBILE);
		retVal.setValue(telefone);
		
		return retVal;
	}
}
