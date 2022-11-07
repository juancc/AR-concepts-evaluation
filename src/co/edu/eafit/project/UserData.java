/*
 * Clase con los datos del usuario que van a ser enviados al servidor
 */

package co.edu.eafit.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.edu.eafit.activities.LogActivity;

public class UserData {
	private static HashMap<Integer, Integer> colors; // colores del contexto
	private static HashMap<String, String> segmentation; // colores del contexto
	private static List<Integer> lighting;//lista de las luces calculadas durante la evaluacion de los conceptos

	
	
	

	//asignar todos los datos
	public UserData( ){
		colors = new HashMap<Integer, Integer>();
		segmentation = new HashMap<String, String>();
		lighting = new ArrayList<Integer>();
	}
	
	public static HashMap<String, String> getSegmentation() {
		return segmentation;
	}

	public static void setSegmentation(HashMap<String, String> segmentation) {
		UserData.segmentation = segmentation;
	}

	public static void setColors(HashMap<Integer, Integer> colors) {
		UserData.colors = colors;
	}

	public static void addLighting(Integer lighting) {
		UserData.lighting.add(lighting);
	}

	public static List<Integer> getLighting(){
		return lighting;
	}
	
	public static  HashMap<Integer, Integer> getColors(){
		return colors;
	}
	

}
