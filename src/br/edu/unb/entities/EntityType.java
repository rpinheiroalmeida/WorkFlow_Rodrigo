package br.edu.unb.entities;

public enum EntityType {

	PROJECT("Project"), ACCOUNT("Account"), AGENT("Agent"), ACTIVITY("Activity"), COLLECTION("Collection");
	
	private String name;
	
	EntityType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
