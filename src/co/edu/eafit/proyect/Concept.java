package co.edu.eafit.proyect;

import saito.objloader.*;


public class Concept {
	private String name; // nombre del concepto
	private OBJModel mesh;
	private String description;
	
	
	public Concept(String conceptName){
		this.name = conceptName;

	}

	public void setMesh(OBJModel mesh){
		this.mesh = mesh;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
}
