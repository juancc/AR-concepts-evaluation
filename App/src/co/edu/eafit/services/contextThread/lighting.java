package co.edu.eafit.services.contextThread;


import ketai.camera.KetaiCamera;
import android.util.Log;
import processing.core.PApplet;
import processing.core.PVector;
import co.edu.eafit.project.Concept;
import co.edu.eafit.services.ar.AR;


	public class lighting{
	
		private static final String tag = "lighting";
		private static int currentLighting; //almacena el color calculado 
		private static int oldLighting = -723984;//si no se pudo calcular que retorne la anterior...(200,200,200)
		private static float MarkerWhiteSpace = 2 ; // espacio que se le quita al marker cuando calcula la luz para evitar tomar objetos del medio
	



			  public static int calcLighting(PApplet p) {
				  int count =0;
				  currentLighting = 0;
				  
			      if (AR.isMarker()){ // si hay un marker
				      Log.i(tag, "Calculating lighting...");
				      //obtener los vertices del marker
				      PVector[] marker2dVertex = AR.getMarkerVertex();
				      float xMax, yMax, xMin, yMin; //valores del marker
				      int leftLimit, rightLimit, lowerLimit, upperLimit; // limites del area
				      
				      
				      float markerHue = 0; 
				      float markerSaturation = 0;
				      float  markerBrightness = 0;
	
				      xMax = marker2dVertex[0].x; // inicialmente es el primero
				      yMax = marker2dVertex[0].y; // inicialmente es el primero
				      xMin = marker2dVertex[0].x; // inicialmente es el primero
				      yMin = marker2dVertex[0].y; // inicialmente es el primero
	
				      for (int i=1; i< marker2dVertex.length ; i++) {
				    	  
				    	  
				    	
				        if (marker2dVertex[i].x > xMax) {
				          xMax = marker2dVertex[i].x;
				        } // si es mas grande/pequeño reemplazar para las x
				        if (marker2dVertex[i].x < xMin) {
				          xMin = marker2dVertex[i].x;
				        }
	
				        if (marker2dVertex[i].y < yMax) {
				          yMax = marker2dVertex[i].y;
				        } // si es mas grande/pequeño reemplazar para las y
				        if (marker2dVertex[i].y > yMin) {
				          yMin = marker2dVertex[i].y;
				        }
				      }
				      
				     
				      //Log.i(tag, "xMax: "+ xMax + " xMin: "+ xMin);
				      
				      //rectMode(CORNER);
				      //scale(1/displayScaleX, 1/displayScaleY );
	
				      
					// limites del rectangulo del marker
				      //p.rect(xMin - MarkerWhiteSpace, yMin + MarkerWhiteSpace  ,    xMax-xMin + MarkerWhiteSpace*2  , yMax-yMin - MarkerWhiteSpace*2);
				      leftLimit = (int) ((xMin + MarkerWhiteSpace));// *1/displayScaleX);
				      rightLimit = (int) ((xMax - MarkerWhiteSpace));//*1/displayScaleX);
				      upperLimit = (int) ((yMin - MarkerWhiteSpace));//* 1/displayScaleY);
				      lowerLimit = (int) ((yMax + MarkerWhiteSpace));//* 1/displayScaleY);
				      
				      KetaiCamera cam = AR.getCam();
				      int camSizeW = cam.width;
				      cam.loadPixels(); //Carga los pixeles de la camara en las dimensiones de captura
				      int[] camPixels= cam.pixels;
	
				      for (int i =  leftLimit; i < rightLimit; i++) {
				        for (int j =  lowerLimit; j < upperLimit; j++ ) { // y crece hacia abajo
				          int pos = i + (j * camSizeW); // en suma por que es un array lineal
				          System.out.println(p.brightness(camPixels[pos]));
				          //Log.i(tag, String.valueOf(pos));
				          if (  p.brightness(camPixels[pos]) > 90 ) { // 0 < brightness < 256
				        	  //Log.i(tag, String.valueOf(p.brightness(camPixels[pos])));
				            markerHue = markerHue+ p.hue(camPixels[pos]);
				            markerSaturation = markerSaturation + p.saturation(camPixels[pos]);
				            markerBrightness = markerBrightness + p.brightness(camPixels[pos]);
	
				            count++;
				          }
				        }
				      }
	
				      markerHue = markerHue / count;
				      markerSaturation = markerSaturation / count; 
				      markerBrightness = markerBrightness / count;
				      p.colorMode(p.HSB);
				      currentLighting = p.color (markerHue, markerSaturation, markerBrightness);
				      p.colorMode(p.RGB);
				      //AmbientLightUpdated = true;
				      
				      //println("Ambient light updated!");
				      Log.i(tag, "R: "+ p.red(currentLighting)+",G: "+ p.green(currentLighting )+",B: "+ p.blue(currentLighting) );
				      //Log.i(tag, String.valueOf(currentLighting));
				      //Log.i(tag, String.valueOf(p.red(AmbientColor)));
			      }
			      
			      //si se pudo calcular la luz retorne sino la anterior calculada
			      if(count > 30){
			    	  oldLighting = currentLighting;
			    	  return currentLighting;
			      }else Log.i(tag, "oldLighting: "+ String.valueOf(oldLighting)); return oldLighting;

			  }
			  
		
			  

			
			  
			  
			  
}
