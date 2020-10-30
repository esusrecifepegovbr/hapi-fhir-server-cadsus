package br.com.gointerop.hapi.fhir.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.r4.model.Patient;

import br.com.gointerop.client.ClientIHEPDQ;
import br.com.gointerop.client.IClient;
import br.com.gointerop.client.request.Request;
import br.com.gointerop.client.request.RequestIHEPDQ;
import br.com.gointerop.hapi.fhir.mapper.IMapper;
import br.com.gointerop.hapi.fhir.mapper.MapperPatient;
import br.com.gointerop.hapi.fhir.parser.IParser;
import br.com.gointerop.hapi.fhir.parser.ParserIHEPDQ;
import br.com.gointerop.hapi.fhir.repository.CADSUSProperties;
import br.com.gointerop.hapi.fhir.translator.ITranslator;
import br.com.gointerop.hapi.fhir.translator.TranslatorFactory;
import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;
import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import ca.uhn.fhir.rest.param.BaseParam;

public final class ControllerPatient extends Controller<Patient> {
	private IMapper iMapping = MapperPatient.getInstance();
	private ITranslator iTranslator = TranslatorFactory.createInstance(TranslatorCatalog.PATIENT);
	private IClient<String> iClient = ClientIHEPDQ.getInstance();
	private IParser<Patient> iParser = ParserIHEPDQ.getInstance();
	
	public static IController<Patient> getInstance() {
		return new ControllerPatient();
	}
	
	@Override
	public List<Patient> search(HashMap<String, BaseParam> params) {
		List<Patient> retVal = new ArrayList<Patient>();
		HashMap<String, BaseParam> translatedValues = iTranslator.translate(params);
		HashMap<String, BaseParam> mappedColumns = iMapping.map(translatedValues);
		
		BaseParam paramCPF;
		String host = null, port = null, method = null, username = null, password = null, cpf = null;
		
		host = CADSUSProperties.getURL();
		method = "POST";
		username = CADSUSProperties.getUSERNAME();
		password = CADSUSProperties.getPASSWORD();
		paramCPF = params.get(Patient.SP_IDENTIFIER);
		cpf = UtilBaseParam.toValue(paramCPF);
		
		Request request = new RequestIHEPDQ(host, port, method, username, password, cpf, null);
		
		String response = null;
		Patient patientResponse = null;
		
		try {
			response = iClient.send(request);
			patientResponse = iParser.parse(response);
			
			retVal.add(patientResponse);
		} catch (Exception e) {
		}
		
		return retVal;
	}

	@Override
	public Patient readById(Long id) {
		return null;
	}
}
