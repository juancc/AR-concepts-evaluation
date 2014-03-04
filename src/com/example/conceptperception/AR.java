package com.example.conceptperception;

import android.util.Log;
import processing.core.*;
import ketai.camera.*;
import jp.nyatla.nyar4psg.*;


public class AR {
	  private MultiMarker nya;
	  private PImage camBuffer;
	  private KetaiCamera cam;
	  private static String tag = "AR";
	  
	  //default values
	  private int  MarkerSise; // tamaño del marker en mm
	  private int numMarkers;// Numero de markers
	  private float mS;//Dimensiones del AR
	  private String camPara;//Direccion completa del archivo camera_para.dat
	  private String patternPath;// Direccion del marker
	  private boolean IsMarker = false;//Variables Camara
	  private int camSizeW;//tamaño de la imagen de captura de la camara, es escalado al tamaño del dispositivo
	  private int camSizeH;
	  private int ftps;
	  PApplet applet;
	  
	  public AR(PApplet newApplet, ARconfig arConfiguration){
		  
		//egl.eglMakeCurrent()
		applet = newApplet;
		

		MarkerSise = arConfiguration.getMarkerSize();
		numMarkers = arConfiguration.getnumMarkers();
		mS = arConfiguration.getmS();

		camPara = arConfiguration.getcamPara();
		patternPath = arConfiguration.getpatternPath();

		camSizeW = arConfiguration.getcamSizeW();
		camSizeH = arConfiguration.getcamSizeH();
		ftps = arConfiguration.getftps();

		// instanciar la camara del dispositivo
		this.cam = new KetaiCamera(applet, camSizeW, camSizeH, ftps);
		cam.start();
		//frameRate(ftps);// actualizacion de los frames de la funcion void,
		
		 //multi marker a una especifica resolucion, con la camara por defecto, sistema coordenado 
		 nya = new MultiMarker(applet, camSizeW, camSizeH, camPara, NyAR4PsgConfig.CONFIG_DEFAULT);
		 //nya = new MultiMarker(applet, camSizeW, camSizeH, camPara);
		 nya.addARMarker(patternPath, MarkerSise);
		 nya.setLostDelay(4);//Retraso cuando un marker no es encontrado
		 

		
		// dibuja en la pantalla
		 
	  
	  }
	  
 


	public void  drawCamera()
	  {  
		applet.hint(applet.DISABLE_DEPTH_TEST);
		applet.camera();//sin camara no muestra la imagen de 
		applet.background(0);
	    
		applet.image(cam, applet.width/2, applet.height/2, applet.width, applet.height);
		
		//update camera
		cam.read();
		
		applet.hint(applet.DISABLE_DEPTH_TEST);
	  }
	
}
