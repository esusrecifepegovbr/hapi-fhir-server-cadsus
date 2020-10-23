package br.com.gointerop.hapi.fhir.translator;

import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;

public class TranslatorFactory {
	public static ITranslator createInstance(TranslatorCatalog resourceType) {
		ITranslator iTranslator = null;

		switch (resourceType) {
		case PATIENT:
			iTranslator = new TranslatorPatient();
			break;
		}
		return iTranslator;
	}
}
