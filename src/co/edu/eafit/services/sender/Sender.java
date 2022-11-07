/*
 * Enviar los datos al servidor
 * Funciones para asignar los datos al usuario
 * 
 * Usa Gson para parsear los datos del usuario(iluminacion, colores, iluminacion, segementacion,) al json
 */
package co.edu.eafit.services.sender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import co.edu.eafit.activities.LogActivity;
import co.edu.eafit.project.Concept;
import co.edu.eafit.project.Project;
import co.edu.eafit.project.UserData;
import co.edu.eafit.services.server.Server;




public class Sender {
	private static final String tag = "Sender";
	
	
	
	
	public static boolean sendData(Project project){
		//boolean isOK = false;
		HttpResponse response = null;
		Log.i(tag, "Sending data");
		
		//UserData data = new UserData(project.getContextColors(), project.getProjectLighting());
		
		
		String json = serializer(project);
		try {
			response= Server.postEvaluation(json, "default");
			//String responseString = EntityUtils.toString(response.getEntity());
			//Log.i(tag, responseString);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

		
		return true;
	}
	
	
	
	
	public static String serializer(Project project){
		Log.i(tag, "Serializing data...");
		UserData data = project.getUserData();
		
		HashMap<String, int[]> DS = getconceptsDS(project); // adquirir los diferenciales semanicos almacenados en cada concepto
		List<List<String>> adjectives = project.getAdjectives();
		//Log.i(tag, "here");
		
		Gson gson = new Gson();
		//String stringSegment = "";
		String stringLighting = "";
		String stringColors = "";
		String stringDS = "";// diferencial semantico de los proyectos
		String stringAdjectives = "";
		
		 HashMap<String, String> segmentation = data.getSegmentation();
		
		
		
		//stringSegment = gson.toJson(data.getSegmentation());
		stringLighting = gson.toJson(data.getLighting());
		stringColors = gson.toJson(data.getColors());
		stringDS = gson.toJson(DS);
		stringAdjectives = gson.toJson(adjectives);
		
		/*
		String json = "{ \"segmentation\": " + stringSegment + 
				", \"lighting\": " + stringLighting +
				", \"colors\": " +stringColors + 
				", \"conceptsEvaluation\": " + stringDS +
				", \"adjectives\": " + stringAdjectives +
				"}";
		 */
		String json = 
				"{"+
					"\"nombre\": \""+ segmentation.get("projectName") +"\","+
					"\"users\": {"+
						"\"genero\":\"" + segmentation.get("gender") +"\","+
						"\"estrato\":\"" + segmentation.get("socialEstrata") +"\","+
						"\"email\": \"" + segmentation.get("email") +"\","+
						"\"edad\": \"" + segmentation.get("age") +"\","+
						"\"profesion\":\"" + segmentation.get("profession") +"\","+
						"\"lighting\":" + stringLighting +","+
						"\"colors\":" +stringColors + ", "+						
						"\"conceptsEvaluation\": " + stringDS +
					"},"+
					"\"adjectives\":" + stringAdjectives +
				"}";
		
		//Log.i(tag,"JSON: "+ json);
		
		return json;
	}
	
	
	
	
	//empaqueta las evaluaciones semanticas de los conceptso en un hashmap con key: conceptName, value: array con las evaluaciones
	private static HashMap<String, int[]>getconceptsDS(Project project){
		HashMap<String, int[]> conceptsEvaluation = new HashMap<String, int[]>();
		ArrayList<Concept> concepts = project.getConcepts();//conceptso del proycto
		
		for(int i=0; i<concepts.size(); i++){
			conceptsEvaluation.put(concepts.get(i).getName(), concepts.get(i).getConceptEvaluation());
		}
		
		
		return conceptsEvaluation;
		
	}
	
	
	
	

	
	//asigna los datos de segmentacion desde el intent
	//retorna el nombre del projecto
	public static  HashMap<String, String> getUserSegmentation(Intent intent){
		// for testing...
		 HashMap<String, String> userSegmentation = new  HashMap<String, String>();//datos de segementacion del usuario
		 Bundle extras = intent.getExtras( );
		 /*
		  * para evitar usar la primer actividad
		  
		 userSegmentation.put("projectName", "default");
		 userSegmentation.put("age", "27");
		 userSegmentation.put("email", "test@gmail.com");
		 userSegmentation.put("socialEstrata", "5");
		 userSegmentation.put("gender", "masculino");
		 userSegmentation.put("profession", "estudiante");
		  */
		 
		
		 
		
		Log.i(tag, "Project recieved: "+ extras.getString(LogActivity.PROJECT_EXTRA));
		
		 userSegmentation.put("projectName", extras.getString(LogActivity.PROJECT_EXTRA));
		 userSegmentation.put("age", extras.getString(LogActivity.AGE_EXTRA));
		 userSegmentation.put("email", extras.getString(LogActivity.EMAIL_EXTRA));
		 userSegmentation.put("socialEstrata", extras.getString(LogActivity.SOCIAL_EXTRA));
		 userSegmentation.put("gender", extras.getString(LogActivity.GENDER_EXTRA));
		 userSegmentation.put("profession", extras.getString(LogActivity.PROFESSION_EXTRA));
		
		 
		return userSegmentation;
	}
	
}
