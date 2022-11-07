package co.edu.eafit.project;

import java.util.HashMap;
import java.util.Hashtable;

import processing.core.PApplet;
import processing.core.PShape;

import saito.objloader.*;


public class Concept {
	private String name; // nombre del concepto
	private OBJModel mesh;
	//private PShape mesh;
	private String description;
	private int[] conceptEvaluation;//indice es el numero correspondiente al adjetivo y el valor es el asignado
	private boolean isEvaluated;

	
	public Concept(String conceptName, int numAdjectives){
		this.name = conceptName;
		conceptEvaluation = new int[numAdjectives];//numero de adjetivos semanticos del proyecto

		

		//conceptEvaluation =  new Hashtable();

	}

	public void setMesh(OBJModel mesh){
		this.mesh = mesh;
		//provisional escala
		mesh.scale(50);//ojo!!! verificar las escalas en blender
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void drawConcept(PApplet p){
		mesh.draw();
	}
	
	public String getName(){
		return name;
	}
	
	//for testing
	public OBJModel getMesh(){
		return mesh;
	}
	
	public void setScale(int adjectiveIndex, int evaluation){ // las escalas comienzan en 1
		  conceptEvaluation[adjectiveIndex] = evaluation;
	}
	
	
	public int[] getEvaluatedScales(){
		    return conceptEvaluation;
	}
	
	public void isEvaluated(){
		isEvaluated = true;
	}
	
	public boolean getEvaluatedStatus(){
		return isEvaluated;
	}
	
	
	public int[] getConceptEvaluation(){
		return conceptEvaluation;
	}
	
	

}
