package org.hl7.fhir.helper;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;

public class HelperCodeableConcept {
	public static CodeableConcept create(String system, String code) {
		CodeableConcept retVal = new CodeableConcept();
		List<Coding> codings = new ArrayList<Coding>();
		
		codings.add(HelperCoding.create(system, code));
		
		retVal.setCoding(codings);
		return retVal;
	}
 }
