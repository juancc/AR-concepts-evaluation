package com.example.conceptperception;

import android.util.Log;

public class ARconfig {
	
	 //Variables Realidad aumentada--------------------------------------
	  private int  MarkerSize; // tamaño del marker en mm
	  private int numMarkers;// Numero de markers
	  private float mS;//Dimensiones del AR
	  private String camPara;//Direccion completa del archivo camera_para.dat
	  private String patternPath;// Direccion del marker
	  private boolean IsMarker;//Variables Camara
	  private int camSizeW;//tamaño de la imagen de captura de la camara, es escalado al tamaño del dispositivo
	  private int camSizeH;
	  private int ftps;
	  
	  private static String tag = "ARconfig";
	  
	  public int getMarkerSize(){
	    return MarkerSize;
	  }
	  public void setMarkerSize(int MarkerSize){
	    this.MarkerSize = MarkerSize;
	    Log.i(tag, "MarkerSize: "+ MarkerSize);
	  }
	  
	  public int getnumMarkers(){
	    return numMarkers;
	  }
	  public void setnumMarkers(int numMarkers){
	    this.numMarkers = numMarkers;
	    Log.i(tag, "MarkerSize: "+ numMarkers);
	  }
	  
	  public float getmS(){
	    return mS;
	  }
	  public void setmS(float mS){
	    this.mS = mS;
	  }
	  
	  public String getcamPara(){
	    return camPara;
	  }
	  public void setcamPara(String camPara){
	    this.camPara = camPara;
	  }
	  
	  public String getpatternPath(){
	    return patternPath;
	  }
	  public void setpatternPath(String patternPath){
	    this.patternPath = patternPath;
	  }
	  
	  public int getcamSizeW(){
	    return camSizeW;
	  }
	  public void setcamSizeW(int camSizeW){
	    this.camSizeW = camSizeW;
	  }

	  public int getcamSizeH(){
	    return camSizeH;
	  }
	  public void setcamSizeH(int camSizeH){
	    this.camSizeH = camSizeH;
	  }
	  
	  public int getftps(){
	    return ftps;
	  }
	  public void setftps(int ftps){
	    this.ftps = ftps;
	  }
	  
}


