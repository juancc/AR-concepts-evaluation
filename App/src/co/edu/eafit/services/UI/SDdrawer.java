package co.edu.eafit.services.UI;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.util.Log;

import processing.core.PApplet;
import processing.core.PVector;
import co.edu.eafit.project.Concept;
import co.edu.eafit.project.Project;

public class SDdrawer {
	private PApplet p;
	private int SemanticDifferentialLarge;
	private List<List<String>> adjectives;
	private final static String tag = "SemanticDifferentialDrawer";
	private  float elementsMargin = 35; // margen a lado y lado de la escala
	
	private int boxColor;//color de la region de los adjetivos
	private int textColor;
	
	private static float posXsendText;//posicion de donde se dibuja  el texto que indica como enviar los datos
	private static float posYsendText;
	
	//verificador de informacion del diferencial semantico
	private SDprocessing myProcessor;
	
	private Helper helper;
	
	public SDdrawer(PApplet applet, Project myProject, int SemanticDifferentialLarge){
		this.p = applet;
		this.adjectives = myProject.getAdjectives();
		this.SemanticDifferentialLarge = SemanticDifferentialLarge;
		this.posXsendText = 2*p.width/3;
		this.posYsendText = 2*p.height/3;
		
		helper = new Helper(p);
		myProcessor = new SDprocessing(myProject);//procesa que el diferencial semantico de los conceptos/proyecto este completo
	}

	public void DrawSD(Concept concept){
		int[] conceptEvaluation = concept.getEvaluatedScales();
		
		float adjectiveDistance = (p.height )/adjectives.size();
	   
	    float XpositionSelectors = (SemanticDifferentialLarge - 2*elementsMargin) / 4;
		
		p.pushStyle();
		p.hint(p.DISABLE_DEPTH_TEST);
		p.noLights();
		p.noStroke();
		p.camera();
		p.rectMode(p.CORNER);
		
		if(concept.getEvaluatedStatus()){//si ya esta evaluado cambiar de color
			boxColor = p.color(10, 154, 0, 220);
			textColor = p.color(255, 255, 255, 220);
			
		}else{
			boxColor = p.color(255, 255, 255, 220);
			textColor = p.color(90, 90, 90, 220);
		}
		
		p.fill(boxColor);
		p.rect(0,0, SemanticDifferentialLarge, p.height);
	    for(int i=0; i<adjectives.size(); i++){
	        p.textSize(20);
	        p.fill(textColor);
	        p.textAlign(p.LEFT, p.TOP);
	        p.text(adjectives.get(i).get(0), 10, i*adjectiveDistance);
	        //Log.i(tag,adjectives.get(i).get(0));
	        p.textAlign(p.RIGHT, p.TOP);
	        
	        p.text(adjectives.get(i).get(1), SemanticDifferentialLarge-10, i*adjectiveDistance);
	        for(int j=0; j<5; j++){
	        	
	        	
	        	if(conceptEvaluation[i]-1 == j && conceptEvaluation[i]-1  != -1){
	        		p.fill(textColor);
	        	}else{ 
	        		p.noFill();
	        	}
	        	
	          p.strokeWeight(2);
	          p.stroke(textColor);
	          p.ellipse( elementsMargin + j*XpositionSelectors, i*adjectiveDistance +35, 20, 20 );
	            
	        }
	      }
	    
	    drawSendButton();//si esta completo dibujar boton de enviar
	    
	    helper.initHelpDrawer();//dibuja la ayuda inicial
	    
	    p.hint(p.ENABLE_DEPTH_TEST);
	    p.popStyle(); 
	    
	}
	
	private PVector defineSD(float x,float y)
	// asigna una puntuacion al diferencial semantico dependiendo del lugar de la pantalla donde el usuario haga click
	{
	  //println(AdjectivesToEvaluate.length);
	  PVector selection = new PVector(5,adjectives.size());// empieza desde una categoria menor
	  // determinar la escala en las x
	  float increasesX = SemanticDifferentialLarge / 5;
	  float increasesY = p.height / adjectives.size();

	  
	  for(int j=4; j>0; j--){// determinar la posicion en las x
	    if (x < j* increasesX){
	      selection.x = j;
	    }
	    else{
	      break;
	    }
	  }
	  
	  
	  for(int i=adjectives.size()-1; i>0; i--){// posicion en y que es el adjetivo
	    if (y < i* increasesY){
	      selection.y = i;
	    }
	    else{
	      break;
	    }
	  }

	  return selection;
	}

	
	 public void setScale(float x, float y, Concept concept){ 
		  PVector selection = defineSD(x, y);
		  //Log.i(tag, String.valueOf(selection.x)+", "+String.valueOf(selection.y-1));
		  concept.setScale((int) selection.y -1, (int) selection.x);// las escalas comienzan en 
	}
	 
	 public void drawSendButton(){
		 if(SDprocessing.getProjectEvaluated()){
			 
			 posYsendText -= (float)  3;
			 
			 if(posYsendText < p.height/2){
				 this.posYsendText = 2*p.height/3;
			 }
			 
			 
			 p.hint(p.DISABLE_DEPTH_TEST);
			 p.pushStyle();
			 p.noLights();
			 p.noStroke();
			 p.camera();
			 
			 p.textSize(35);
			 p.text("Deslice hacia arriba para enviar",posXsendText, posYsendText );
			 
			 
			 p.hint(p.ENABLE_DEPTH_TEST);
			 p.popStyle(); 
		 }
	 }
	 
	 public Helper myHelper(){
		 return helper;
	 }
	
}
