package br.com.gointerop.hapi.fhir.coding;

import org.hl7.fhir.valueset.Identifier;

public class CodingPatientIdentifier extends Coding {
	private String value = null;
	
	public CodingPatientIdentifier(String url, Identifier.value tax) {
		super(url);
		this.value = tax.toString();
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
