package br.com.gointerop.client.request;

public class RequestIHEPDQ implements Request {
	private static final String CONTENT_TYPE = "application/soap+xml;charset=UTF-8;action=\"urn:hl7-org:v3:PRPA_IN201305UV02\"";

	private String host;
	private String port;
	private String method;
	private String contentType = CONTENT_TYPE;
	private String username;
	private String password;
	private String cpf;
	private String cns;
	
	public RequestIHEPDQ(String host, String port, String method, String username, String password,
			String cpf, String cns) {
		super();
		this.host = host;
		this.port = port;
		this.method = method;
		this.username = username;
		this.password = password;
		this.cpf = cpf;
		this.cns = cns;
	}

	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public String getPort() {
		return this.port;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getBody() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:urn=\"urn:ihe:iti:xds-b:2007\" xmlns:urn1=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\" xmlns:urn2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:urn3=\"urn:ihe:iti:xds-b:2007\">");
		sb.append("		   <soap:Header>");
		sb.append("		      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">");
		sb.append("		         <wsse:UsernameToken wsu:Id=\"Id-0001334008436683-000000002c4a1908-1\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">");
		sb.append("		            <wsse:Username>"+this.username+"</wsse:Username>");
		sb.append("		            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+this.password+"</wsse:Password>");
		sb.append("		         </wsse:UsernameToken>");
		sb.append("		      </wsse:Security>");
		sb.append("		   </soap:Header>");
		sb.append("		   <soap:Body>");
		sb.append("		      <PRPA_IN201305UV02 xsi:schemaLocation=\"urn:hl7-org:v3 ./schema/HL7V3/NE2008/multicacheschemas/PRPA_IN201305UV02.xsd\" ITSVersion=\"XML_1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"urn:hl7-org:v3\">");
		sb.append("		         <id root=\"2.16.840.1.113883.4.714\" extension=\"123456\"/>");
		sb.append("		         <creationTime value=\"20070428150301\"/>");
		sb.append("		         <interactionId root=\"2.16.840.1.113883.1.6\" extension=\"PRPA_IN201305UV02\"/>");
		sb.append("		         <processingCode code=\"T\"/>");
		sb.append("		         <processingModeCode code=\"T\"/>");
		sb.append("		         <acceptAckCode code=\"AL\"/>");
		sb.append("		         <receiver typeCode=\"RCV\">");
		sb.append("		            <device classCode=\"DEV\" determinerCode=\"INSTANCE\">");
		sb.append("		               <id root=\"2.16.840.1.113883.3.72.6.5.100.85\"/>");
		sb.append("		            </device>");
		sb.append("		         </receiver>");
		sb.append("		         <sender typeCode=\"SND\">");
		sb.append("		            <device classCode=\"DEV\" determinerCode=\"INSTANCE\">");
		sb.append("		               <id root=\"2.16.840.1.113883.3.72.6.2\"/>");
		sb.append("		               <name>CADSUS</name>");
		sb.append("		            </device>");
		sb.append("		         </sender>");
		sb.append("		         <controlActProcess classCode=\"CACT\" moodCode=\"EVN\">");
		sb.append("		            <code code=\"PRPA_TE201305UV02\" codeSystem=\"2.16.840.1.113883.1.6\"/>");
		sb.append("		            <queryByParameter>");
		sb.append("		               <queryId root=\"1.2.840.114350.1.13.28.1.18.5.999\" extension=\"1840997084\"/>");
		sb.append("		               <statusCode code=\"new\"/>");
		sb.append("		               <responseModalityCode code=\"R\"/>");
		sb.append("		               <responsePriorityCode code=\"I\"/>");
		sb.append("		               <parameterList>");
		
		if(this.cns != null && !this.cns.equals("")) {
			
			sb.append("		                  <livingSubjectId>");
			sb.append("		                     <value root=\"2.16.840.1.113883.13.236\" extension=\""+this.cns+"\"/>");
			sb.append("		                     <semanticsText>LivingSubject.id</semanticsText>");
			sb.append("		                  </livingSubjectId>");
		
		}
		
		if(this.cpf != null && !this.cpf.equals("")) {
			
			sb.append("		                  <livingSubjectId>");
			sb.append("		                     <value root=\"2.16.840.1.113883.13.237\" extension=\""+this.cpf+"\"/>");
			sb.append("		                     <semanticsText>LivingSubject.id</semanticsText>");
			sb.append("		                  </livingSubjectId>");
		
		}
		
		sb.append("		               </parameterList>");
		sb.append("		            </queryByParameter>");
		sb.append("		         </controlActProcess>");
		sb.append("		      </PRPA_IN201305UV02>");
		sb.append("		   </soap:Body>");
		sb.append("		</soap:Envelope>");
		
		return sb.toString();
	}

}
