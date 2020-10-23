package br.com.gointerop.client.request;

public interface Request {
	public String getHost();
	public String getPort();
	public String getMethod();
	public String getUsername();
	public String getPassword();
	public String getContentType();
	public String getBody();
}
