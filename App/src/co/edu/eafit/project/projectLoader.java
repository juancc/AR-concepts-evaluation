
/*
 * Crea os objetos tipo concept del proyecto
 */
package co.edu.eafit.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

import saito.objloader.OBJModel;

import co.edu.eafit.project.*;

import android.util.Log;

public class projectLoader implements IprojectLoader {
	private String projectFolder;
	private final static String tag = "projectLoader"; 
	private Project myProject;
	private String projectName;
	
	public projectLoader(String projectFolder, String projectName){
		this.projectFolder = projectFolder + "/concepts";
		this.projectName = projectName;
		myProject = new Project(projectName);
	}
	
	
	@Override
	public Project loadProjects(PApplet p) {
		
		Log.i(tag, "Loading project...");
		
		//cargar diferencial semantico
		List<List<String>> adjectives = loadAdjectiveList();
		myProject.setAdjectives(adjectives);
		
		int numAdjectives = adjectives.size();
		
		
		//load concepts
        File conceptsFolder = new File(projectFolder);
        if (conceptsFolder.exists()){
       	 	File[] files = conceptsFolder.listFiles();
       	 	for(int i=0; i<files.length; i++){
       	 		File file = files[i];
       	 		String fileName = file.getName();
       	 		String name = removeExtention(fileName);
       	 		
       	 		String ext = null;
       	 		int index = fileName.lastIndexOf('.');
       	 		if (index > 0 && index < fileName.length() - 1) {
       	 			ext = fileName.substring(index + 1).toLowerCase();
       	 			if (ext.equals("obj")){
       	 				Concept concept = new Concept(name, numAdjectives);
       	 				OBJModel mesh = new OBJModel(p, conceptsFolder+"/"+fileName , "absolute", p.TRIANGLES);
       	 				//mesh.setTexturePathMode("absolute");
       	 				//mesh.disableTexture();
       	 				//PShape mesh =  p.loadShape(conceptsFolder+"/"+fileName);
       	 				
       	 				//buscar la textura... porque no la toma automaticamente
       	 				/*
       	 				PImage texture = null;
	       	 			for(int j=0; j<files.length; j++){
	       	 				File textFile = files[j];
	       	 				
	       	 				String textFileName = textFile.getName();
	       	 				String textExt = textFileName.substring(index + 1).toLowerCase();
	       	 				Log.i(tag, textFileName);
	       	 				if(textExt.equals("jpg") || textExt.equals("png")){
		       	 				String textName = removeExtention(fileName);
		       	 				if(	textName == name ){
	       	 						
	       	 						texture = p.loadImage(fileName);
	       	 					}
	       	 				}
	       	 			}
	       	 			if(texture != null){
	       	 				mesh.setTexture(texture);
	       	 			}
	       	 			*/
	       	 			
	       	 			
       	 				//mesh.scale(50);//ojo!!! verificar las escalas en blender
       	 				//mesh.translateToCenter();
       	 				
       	 				//Log.i(tag,fileName);
       	 				concept.setMesh(mesh);
       	 				
       	 				myProject.addConcept(concept);
       	 				
       	 			}
       	 		}
       	 	}
       	 	
       	 	
       	 	
        }else{
        	
        }
		
		
		
		return myProject;
	}

	
	private List<List<String>> loadAdjectiveList(){
		List<List<String>> adjectivesList = new ArrayList<List<String>>();

		
		//Get the text file
		File file = new File(projectFolder,"adjetivos.txt");
		if (file.exists()){

			//Read text from file
			StringBuilder text = new StringBuilder();
	
			try {
			    BufferedReader br = new BufferedReader(new FileReader(file));
			    String line;
			    
			    while ((line = br.readLine()) != null) {
			    	Log.i(tag,"adjetivos: "+ line);
			        String[] adjectivesLine = line.split("\\,");
			        List<String> adjectiveLine = new ArrayList<String>();
			        adjectiveLine.add(adjectivesLine[0]);
			        adjectiveLine.add(adjectivesLine[1]);
			        adjectivesList.add(adjectiveLine);

			    }
			}
			catch (IOException e) {
			    //You'll need to add proper error handling here
			}
		}else{//no existe la lista de adjetivos cargar una por defecto
			Log.i(tag, "Adjective list not found");
		}
		
		return adjectivesList;
	}
	
	// from: http://stackoverflow.com/questions/3449218/remove-filename-extension-in-java
	/*
	 *  Credit goes to Justin 'jinguy' Nelson for providing the basis of this method:
	 */
	private static String removeExtention(String filePath) {
	    // These first few lines the same as Justin's
	    File f = new File(filePath);

	    // if it's a directory, don't remove the extention
	    if (f.isDirectory()) return filePath;

	    String name = f.getName();

	    // Now we know it's a file - don't need to do any special hidden
	    // checking or contains() checking because of:
	    final int lastPeriodPos = name.lastIndexOf('.');
	    if (lastPeriodPos <= 0)
	    {
	        // No period after first character - return name as it was passed in
	        return filePath;
	    }
	    else
	    {
	        // Remove the last period and everything after it
	        File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
	        return renamed.getPath();
	    }
	}
	
	
	
}
