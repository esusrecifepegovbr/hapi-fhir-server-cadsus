package org.hl7.fhir.helper;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.Patient.ContactComponent;

public class HelperContactComponent {
	public static ContactComponent create(String telefone) {
		ContactComponent retVal = new ContactComponent();
		List<ContactPoint> contactPoints = new ArrayList<ContactPoint>();
		
		contactPoints.add(HelperContactPoint.create(telefone));
		retVal.setTelecom(contactPoints);
		
		return retVal;
	}
}
