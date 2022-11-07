package co.edu.eafit.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Project {
	private String proyectName;
	private ArrayList<Concept> conceptsOfProject;
	private static boolean isProjectEvaluated;
	
	private static UserData user;	
	
	private List<List<String>> adjectives;//adjetivos que componen el diferencial semantico
	
	
	
	
	public Project(String proyectName){
		this.proyectName = proyectName;
		conceptsOfProject = new ArrayList<Concept>();
		
		user = new UserData();
	}
	
	public void addConcept(Concept concept){
		conceptsOfProject.add(concept);
		
	}
	
	public Concept getConcept(int i){
		if(i>-1 && i<conceptsOfProject.size()){
			return conceptsOfProject.get(i);
		}
		else{
			return null;
		}
	}
	
	public int numberOfConcepts(){
		return conceptsOfProject.size();
	}
	
	public void setAdjectives(List<List<String>> adjectives){
		this.adjectives = adjectives; 
	}
	
	public List<List<String>> getAdjectives(){
		return this.adjectives;
	}

	public ArrayList<Concept>  getConcepts(){
		return conceptsOfProject;
		
	}
	
	public static void addLighting(int currentLighting){
		user.addLighting(currentLighting);
	}
	
	public static List<Integer> getProjectLighting(){
		return user.getLighting();
	}
	
	public static void setContextColors( HashMap<Integer, Integer> newContextColors){
		user.setColors(newContextColors);
	}
	
	public static  HashMap<Integer, Integer>getContextColors(){
		return user.getColors();
	}
	
	
	public static void setSegmentation(HashMap<String, String> segmentation){
		user.setSegmentation(segmentation);
	}
	
	
	public static UserData getUserData(){
		return user;
	}
	
}
