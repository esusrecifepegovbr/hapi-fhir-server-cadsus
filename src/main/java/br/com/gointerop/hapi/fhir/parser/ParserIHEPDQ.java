package br.com.gointerop.hapi.fhir.parser;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hl7.fhir.helper.HelperContactComponent;
import org.hl7.fhir.helper.HelperHumanName;
import org.hl7.fhir.helper.HelperIdentifier;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Patient.ContactComponent;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import br.com.gointerop.hapi.fhir.util.UtilDate;
import br.com.gointerop.hapi.fhir.util.UtilString;
import br.com.gointerop.hapi.fhir.util.UtilXML;
import br.gov.pe.recife.esus.system.PatientIdentifier;

public class ParserIHEPDQ extends Parser<Patient> {
	private static ParserIHEPDQ instance;

	public static IParser<Patient> getInstance() {
		if (ParserIHEPDQ.instance == null) {
			ParserIHEPDQ.instance = new ParserIHEPDQ();
		}

		return ParserIHEPDQ.instance;
	}

	@Override
	public Patient parse(String content) throws SAXException, IOException, ParserConfigurationException, ParseException {
		Patient retVal = new Patient();
		List<Identifier> identifiers = new ArrayList<Identifier>();
		List<HumanName> humanNames = new ArrayList<HumanName>();
		List<ContactComponent> contacts = new ArrayList<ContactComponent>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(content));

		Document document = builder.parse(is);
		
		String cpf = UtilXML.getAttributeByTag(document, "value", "extension");

		String given = UtilXML.getContentByTag(document, "given");
		
		String administrativeGender = UtilXML.getAttributeByTag(document, "administrativeGenderCode", "code");

		String telefone = UtilXML.getAttributeByTag(document, "telecom", "value");
		telefone = UtilString.numbersOnly(telefone);
	
		String birthTime = UtilXML.getAttributeByTag(document, "birthTime", "value");
		
		if(cpf != null) { 
			identifiers.add(HelperIdentifier.create(PatientIdentifier.URL, org.hl7.fhir.valueset.Identifier.value.TAX.toString(), cpf));
			retVal.setIdentifier(identifiers);
			retVal.setId(cpf);
		}
		
		if(given != null) {
			humanNames.add(HelperHumanName.create(given));
			retVal.setName(humanNames);
		}
		
		if(administrativeGender != null) {
			switch(administrativeGender) {
			case "M":
				retVal.setGender(Enumerations.AdministrativeGender.MALE);
				break;
			case "F":
				retVal.setGender(Enumerations.AdministrativeGender.MALE);
				break;
			default:
				retVal.setGender(Enumerations.AdministrativeGender.MALE);
			}
		}
		
		if(telefone != null) contacts.add(HelperContactComponent.create(telefone));
		if(birthTime != null) retVal.setBirthDate(UtilDate.fromStringMask(birthTime, "yyyyMMddHHmmss")); 

		return retVal;
	}
}
