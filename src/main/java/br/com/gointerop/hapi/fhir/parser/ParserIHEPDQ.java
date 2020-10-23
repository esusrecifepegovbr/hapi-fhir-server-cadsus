package br.com.gointerop.hapi.fhir.parser;

import org.hl7.fhir.r4.model.Patient;

public class ParserIHEPDQ extends Parser<Patient> {

	public static IParser<Patient> getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Patient parse(String content) {
		Patient retVal = new Patient();
		
		return retVal;
	}

	
}
