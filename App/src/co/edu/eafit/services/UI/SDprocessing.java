package co.edu.eafit.services.UI;
// funciones para procesar la la informacion, diferencial semantico, colores e iluminacion

import java.util.ArrayList;

import co.edu.eafit.project.Concept;
import co.edu.eafit.project.Project;



public class SDprocessing {
	private Project project;
	private static boolean[] differentialSemantics;// booleano de los ds de los conceptos, verdadero si esta completo
	private static boolean isProjectEvaluated;
	private static ArrayList<Concept>  projectConcepts; 
	
	
	public SDprocessing(Project project){
		this.project = project;
		projectConcepts = project.getConcepts();
	}
	
	
	

	public static void isSDcomplete(Concept concept){
		if(!concept.getEvaluatedStatus()){
			int[] conceptEvaluation = concept.getEvaluatedScales();
			int evaluatedAdjectives = 0;
			for(int i=0; i<conceptEvaluation.length; i++){
				if(conceptEvaluation[i]-1  != -1){
					evaluatedAdjectives++;
	        	}
			}
			if(evaluatedAdjectives == conceptEvaluation.length ){
				concept.isEvaluated();
			}
			setIsprojectEvaluated();// actualizar si estan evaluados todos los conceptos
		}	        
	}
	
	public static boolean getProjectEvaluated(){
		return isProjectEvaluated;
		
	}
	
	private static void setIsprojectEvaluated(){
		if(!isProjectEvaluated){
			for(int i=0; i<projectConcepts.size(); i++){
				if(!projectConcepts.get(i).getEvaluatedStatus()){
					isProjectEvaluated = false;
					break;
				}else{
					isProjectEvaluated = true;
				}
			}
		}
		
	}
}
