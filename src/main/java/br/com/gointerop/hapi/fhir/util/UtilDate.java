package br.com.gointerop.hapi.fhir.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilDate {
	public static String toISOString(Date date) {
		// Conversion
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}
