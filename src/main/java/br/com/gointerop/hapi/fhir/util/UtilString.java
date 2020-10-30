package br.com.gointerop.hapi.fhir.util;

public class UtilString {
	public static String numbersOnly(String content) {
		return content.replaceAll("[^\\d.]", "");
	}
}
