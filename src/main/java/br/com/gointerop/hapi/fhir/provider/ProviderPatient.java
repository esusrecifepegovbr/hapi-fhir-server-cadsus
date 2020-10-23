package br.com.gointerop.hapi.fhir.provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Patient;

import br.com.gointerop.hapi.fhir.controller.ControllerPatient;
import br.com.gointerop.hapi.fhir.controller.IController;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.NumberParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;

public class ProviderPatient implements IResourceProvider {
	private IController<Patient> iControllerPatient = ControllerPatient.getInstance();

	FhirContext fhirContext;

	protected Map<String, TreeMap<Long, Patient>> myIdToVersionToResourceMap = Collections
			.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, LinkedList<Patient>> myIdToHistory = Collections.synchronizedMap(new LinkedHashMap<>());
	protected LinkedList<Patient> myTypeHistory = new LinkedList<>();

	public ProviderPatient(FhirContext fhirContext) {
		this.fhirContext = fhirContext;
	}

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Patient.class;
	}

	@Read(version = true)
	public Patient read(@IdParam IIdType theId, RequestDetails theRequestDetails) {
		return iControllerPatient.readById(theId.getIdPartAsLong());
	}

	@Search
	public List<Patient> search(
			@OptionalParam(name = Patient.SP_RES_ID) NumberParam _id, 
			@OptionalParam(name = Patient.SP_RES_LANGUAGE) StringParam _language, 
			@OptionalParam(name = Patient.SP_BIRTHDATE) DateParam birthDate, 
			@OptionalParam(name = Patient.SP_DECEASED) StringParam deceased, 
			@OptionalParam(name = Patient.SP_ADDRESS_STATE) StringParam addressState,
			@OptionalParam(name = Patient.SP_GENDER) StringParam gender,
			@OptionalParam(name = Patient.SP_LINK) StringParam link,
			@OptionalParam(name = Patient.SP_LANGUAGE) StringParam language,
			@OptionalParam(name = Patient.SP_ADDRESS_COUNTRY) StringParam addressCountry,
			@OptionalParam(name = Patient.SP_DEATH_DATE) StringParam deathDate,
			@OptionalParam(name = Patient.SP_PHONETIC) StringParam phonetic,
			@OptionalParam(name = Patient.SP_TELECOM) StringParam telecom,
			@OptionalParam(name = Patient.SP_ADDRESS_CITY) StringParam addressCity,
			@OptionalParam(name = Patient.SP_EMAIL) StringParam email,
			@OptionalParam(name = Patient.SP_GIVEN) StringParam given,
			@OptionalParam(name = Patient.SP_IDENTIFIER) StringParam identifier,
			@OptionalParam(name = Patient.SP_ADDRESS) StringParam address,
			@OptionalParam(name = Patient.SP_GENERAL_PRACTITIONER) StringParam generalPractitioner,
			@OptionalParam(name = Patient.SP_ACTIVE) StringParam active,
			@OptionalParam(name = Patient.SP_ADDRESS_POSTALCODE) StringParam addressPostalCode,
			@OptionalParam(name = Patient.SP_PHONE) StringParam phone,
			@OptionalParam(name = Patient.SP_ORGANIZATION) StringParam organization,
			@OptionalParam(name = Patient.SP_ADDRESS_USE) StringParam addressUse,
			@OptionalParam(name = Patient.SP_NAME) StringParam name,
			@OptionalParam(name = Patient.SP_FAMILY) StringParam family
			) {
		
		HashMap<String, BaseParam> params = new HashMap<String, BaseParam>();		
		
		params.put(Patient.SP_RES_ID, _id); 
		params.put(Patient.SP_RES_LANGUAGE, _language);
		params.put(Patient.SP_BIRTHDATE, birthDate);
		params.put(Patient.SP_DECEASED, deceased);
		params.put(Patient.SP_ADDRESS_STATE, addressState);
		params.put(Patient.SP_GENDER, gender);
		params.put(Patient.SP_LINK, link);
		params.put(Patient.SP_LANGUAGE, language);
		params.put(Patient.SP_ADDRESS_COUNTRY, addressCountry);
		params.put(Patient.SP_DEATH_DATE, deathDate);
		params.put(Patient.SP_PHONETIC, phonetic);
		params.put(Patient.SP_TELECOM, telecom);
		params.put(Patient.SP_ADDRESS_CITY, addressCity);
		params.put(Patient.SP_EMAIL, email);
		params.put(Patient.SP_GIVEN, given);
		params.put(Patient.SP_IDENTIFIER, identifier);
		params.put(Patient.SP_ADDRESS, address);
		params.put(Patient.SP_GENERAL_PRACTITIONER, generalPractitioner);
		params.put(Patient.SP_ACTIVE, active);
		params.put(Patient.SP_ADDRESS_POSTALCODE, addressPostalCode);
		params.put(Patient.SP_PHONE, phone);
		params.put(Patient.SP_ORGANIZATION, organization);
		params.put(Patient.SP_ADDRESS_USE, addressUse);
		params.put(Patient.SP_NAME, name);
		params.put(Patient.SP_FAMILY, family);
		
		return iControllerPatient.search(params);
	}

}