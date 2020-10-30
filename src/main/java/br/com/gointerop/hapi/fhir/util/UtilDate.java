package br.com.gointerop.hapi.fhir.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**19851114000000 **/

public class UtilDate {
	public static String toISOString(Date date) {
		// Conversion
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static Date fromStringMask(String isoString, String mask) throws ParseException {
		DateFormat df1 = new SimpleDateFormat(mask);
		return df1.parse(isoString);
	}
}
