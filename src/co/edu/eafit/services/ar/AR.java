package co.edu.eafit.services.ar;

import co.edu.eafit.project.Project;
import co.edu.eafit.services.UI.SDdrawer;
import co.edu.eafit.services.contextThread.contextThread;
import android.util.Log;
import processing.core.*;
import saito.objloader.OBJModel;
import ketai.camera.*;
import jp.nyatla.nyar4psg.*;


public class AR {
	  private static MultiMarker nya;
	  private PImage camBuffer;
	  private static KetaiCamera cam;
	  private static final String tag = "AR";
	  
	  //default values
	  private int  MarkerSize; // tamaño del marker en mm
	  private int numMarkers;// Numero de markers
	  private float mS;//Dimensiones del AR
	  private String camPara;//Direccion completa del archivo camera_para.dat
	  private String patternPath;// Direccion del marker
	  private boolean IsMarker = false;//Variables Camara
	  private int camSizeW;//tamaño de la imagen de captura de la camara, es escalado al tamaño del dispositivo
	  private int camSizeH;
	  private int ftps;
	  private PApplet p;
	  private static int markerIndex = 0;
	  
	  private SDdrawer sdDrawer;
	  private int semanticDifferentialLarge;
	  private int contextThreadRefresh = 1000; // cada cuanto se actualiza la iluminacion en milisegundos
	  
	  public AR(PApplet p, ARconfig arConfiguration, Project myProject){
		  
		//egl.eglMakeCurrent()
		this.p = p;
		

		MarkerSize = arConfiguration.getMarkerSize();
		numMarkers = arConfiguration.getnumMarkers();
		mS = arConfiguration.getmS();

		camPara = arConfiguration.getcamPara();
		patternPath = arConfiguration.getpatternPath();

		camSizeW = arConfiguration.getcamSizeW();
		camSizeH = arConfiguration.getcamSizeH();
		ftps = arConfiguration.getftps();

		Log.i(tag,String.valueOf(camSizeW));
		
		// instanciar la camara del dispositivo
		this.cam = new KetaiCamera(p, camSizeW, camSizeH, ftps);
		cam.start();
		//frameRate(ftps);// actualizacion de los frames de la funcion void,
		
		 //multi marker a una especifica resolucion, con la camara por defecto, sistema coordenado 
		//Log.i(tag, camPara);
		/*
		 * olversion
		 nya = new MultiMarker(applet, camSizeW, camSizeH, camPara, NyAR4PsgConfig.CONFIG_PSG);
		 //------nya = new MultiMarker(applet, camSizeW, camSizeH, camPara);
		 nya.addNyIdMarker(patternPath, MarkerSize);
		 nya.setLostDelay(4);//Retraso cuando un marker no es encontrado
		 */

		nya=new MultiMarker(p,camSizeW,camSizeH,camPara,NyAR4PsgConfig.CONFIG_PSG);
		nya.addARMarker(patternPath, MarkerSize);
		// dibuja en la pantalla
		
		//dibuja los diferenciales semanticos
		semanticDifferentialLarge = p.width/4;
		sdDrawer = new SDdrawer(p, myProject, semanticDifferentialLarge);
		
		//calculo de servicios de color: ilumincion y colores del contexto
		contextThread contextCalculator = new contextThread(contextThreadRefresh, "contextThread", p, myProject);
		contextCalculator.start();
		 
	  
	  }
	  
	  
 

	public void  drawCamera()
	  {  
		
		
		p.hint(p.DISABLE_DEPTH_TEST);
		p.camera();//sin camara no muestra la imagen de 
		p.background(0);
	    

		p.image(cam, 0, 0, p.width, p.height);
		//update camera
		cam.read();
		
		p.hint(p.ENABLE_DEPTH_TEST);
	  }
	
	public boolean setTansformation(){

		nya.detect(cam);
		if (isMarker()){
			//Generar perspectiva ------------------------------------------------------------------------
			nya.setARPerspective();
			//beginRecord("joons.OBJWriter",""); // desde aca se muetra en el renderizador 
	
			p.setMatrix(nya.getMarkerMatrix(markerIndex)); //Matriz de transformacion de acuerdo al marcador
			return true;
		}
		else  return false;

	}
	
	public static boolean isMarker(){
		return nya.isExistMarker(markerIndex);
	}
	
	public static PVector[] getMarkerVertex(){
		return nya.getMarkerVertex2D(markerIndex);
	}
	
	public static KetaiCamera getCam(){
		return cam;
	}
	
	
	public void setLights(int currentLighting){
		p.directionalLight(  p.red(currentLighting), p.green(currentLighting), p.blue(currentLighting), 0,0,-10  );
		p.directionalLight(  p.red(currentLighting), p.green(currentLighting), p.blue(currentLighting), 10,10,-10  );
		p.directionalLight(  p.red(currentLighting), p.green(currentLighting), p.blue(currentLighting), 10,-10,-10  );
	}
	
	public SDdrawer getSDdrawer(){
		return sdDrawer;
	}
}
