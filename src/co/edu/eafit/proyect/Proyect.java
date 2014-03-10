package co.edu.eafit.proyect;

import java.util.ArrayList;


public class Proyect {
	private String proyectName;
	private ArrayList<Concept> conceptsOfProyect;
	
	
	public Proyect(String proyectName){
		this.proyectName = proyectName;
		conceptsOfProyect = new ArrayList<Concept>();
	}
	
	public void addConcept(Concept concept){
		conceptsOfProyect.add(concept);
		
	}

}
