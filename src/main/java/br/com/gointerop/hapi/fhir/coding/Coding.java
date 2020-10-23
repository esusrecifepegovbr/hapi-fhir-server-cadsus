package br.com.gointerop.hapi.fhir.coding;

public abstract class Coding implements ICoding {
	private String url;
	
	public Coding(String url) {
		this.url = url;
	}
	
	public String getSystem() {
		return this.url;
	}
}
