package br.com.gointerop.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import br.com.gointerop.client.request.Request;

public class ClientIHEPDQ implements IClient<String> {
	public static ClientIHEPDQ instance;
	
	public static ClientIHEPDQ getInstance() {
		if(ClientIHEPDQ.instance == null) {
			ClientIHEPDQ.instance = new ClientIHEPDQ();
		}
		
		return ClientIHEPDQ.instance;
	}
	
	@Override
	public String send(Request request) throws Exception {
		try {
			URL urlWS = new URL(request.getHost());
			HttpURLConnection con = null;
			
			if (urlWS.getProtocol().equalsIgnoreCase("https")) {
				con = (HttpsURLConnection) urlWS.openConnection();
			} else {
				con = (HttpURLConnection) urlWS.openConnection();
			}
			
			if (request.getContentType() != null) {
				con.setRequestProperty("Content-type", request.getContentType());
			} 

			con.setRequestProperty("Transfer-Encoding", "chunked");

			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			OutputStream reqStream = con.getOutputStream();
			OutputStreamWriter wout = null;

			wout = new OutputStreamWriter(reqStream, "UTF-8");
			wout.write(request.getBody());
			wout.flush();

			InputStreamReader input = null;

			if (con.getResponseCode() == 200) {
				input = new InputStreamReader(con.getInputStream());
			} else {
				input = new InputStreamReader(con.getErrorStream());
			}

			StringBuilder retVal = new StringBuilder();
			BufferedReader retornoWs = new BufferedReader(input);
			
			String line;
			
			while ((line = retornoWs.readLine()) != null)
				retVal.append(line);
			
			retornoWs.close();
			
			con.disconnect();
			
			return retVal.toString();
		} catch (MalformedURLException e) {
			throw new Exception("URL invalida em " + request.getHost(), e);
		} catch (IOException e) {
			throw new Exception("Timeout ao esperar pelos dados em " + request.getHost(), e);
		}
	}
}
