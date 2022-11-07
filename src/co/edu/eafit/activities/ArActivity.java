package co.edu.eafit.activities;


import java.util.HashMap;
import java.util.Hashtable;

import ketai.ui.KetaiGesture;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;

import android.view.MotionEvent;
import co.edu.eafit.project.Concept;
import co.edu.eafit.project.IprojectLoader;
import co.edu.eafit.project.Project;
import co.edu.eafit.project.projectLoader;
import co.edu.eafit.services.UI.SDdrawer;
import co.edu.eafit.services.UI.Helper;
import co.edu.eafit.services.UI.SDprocessing;
import co.edu.eafit.services.ar.AR;
import co.edu.eafit.services.ar.ARconfig;
import co.edu.eafit.services.ar.Iconfig;
import co.edu.eafit.services.ar.LoadAR;
import co.edu.eafit.services.contextThread.contextThread;
import co.edu.eafit.services.contextThread.lighting;
import co.edu.eafit.services.gestures.gesturesActions;
import co.edu.eafit.services.sender.Sender;
import processing.core.*;



/*
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
*/

public class ArActivity extends PApplet{
	
	
	private HashMap<String, String> segmentation;//segmentacion del usuario, se recibe del la actividad inicial
	private String projectName;
	private Context ctx;
	private String tag = "ArActivity";
	private Iconfig Configuration;
	private IprojectLoader myProjectLoader;
	private AR augmentedReality;
	private ARconfig arConfiguration;
	private Project myProject;
	
	private boolean assetsReady;
	
	private KetaiGesture gesture;
	
	//variables de la modelacion 3d
	private static Concept currentConcept;
	private int currentConceptIndex=0;
	private int modelListlenght=0;
	
	//diferencial semantico
	//private SDdrawer sdDrawer;
	//private int semanticDifferentialLarge;

	//variables del calculo de servicios de color

	
	

	private Sender sender;// genera el json y lo envia al servidor
	
	
	//private Helper myhelper;
	
	
	//informacion del contexto
	private static int currentLighting; 
	
	//for testing 
	//private OBJModel model;
	//private PShape s;
	private PShape s;
	
	
	public static void main (String args[]){
		PApplet.main(new String[]{"-present", "something.whatever"});	
	}
	
	
	
	 
	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
		orientation(LANDSCAPE);
		sketchRenderer();//especifica el tipo de renderizador p3d
		
		
		gesture = new KetaiGesture(this); // Manejo de gestos

		 /*
		 * vamos a iniciar esta actividad primero para evitar connexion con el servidor....
		Intent intent = getIntent();
		projectName = intent.getStringExtra(LogActivity.PROJECT_EXTRA);
		*/
		//recive los datos del usuario: segmentacion y los agrega a un hashmap
		Intent intent = getIntent();
		segmentation = Sender.getUserSegmentation(intent);
		projectName = segmentation.get("projectName");
	
		
		
		Log.i(tag, "Initializing: " + projectName);
		
		Configuration = new LoadAR(projectName, this);// objeto que crea la configuracion de todos los objetos
		Configuration.computeConfiguration();
		//arConfiguration = Configuration.getArConfig();// configuracion del AR
		
		
		String projectFolder = Configuration.getProjectFolder();
		
		//project variables
		myProjectLoader = new projectLoader(projectFolder, projectName);
		myProject = myProjectLoader.loadProjects(this);
		myProject.setSegmentation(segmentation);
		
		augmentedReality = Configuration.getAR(myProject);
		
		modelListlenght = myProject.numberOfConcepts();
		
		
		//augmentedReality = new AR(this, arConfiguration);
		
		//dibuja los diferenciales semanticos
		//semanticDifferentialLarge = width/4;
		//sdDrawer = new SDdrawer(this, myProject, semanticDifferentialLarge);
		
		
		
		
		
		
		
		//myhelper = new Helper(this);
		
		
		
		//testing
		/*
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/"+
				"perception/default/default.zipdefault/concepts/"+
				"2.obj";
				
		Log.i(tag,path );
		s = loadShape(path);
		
		s.scale(20, 20, 20);
		*/
		/*
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/"+
				"perception/default/default.zipdefault/concepts/"+
				"1.obj";
		s = new PShapeOBJ(this, "1.obj");
		s.scale(20);
		*/
		
	}
	
	@Override
	public void draw() {
		
		currentConcept = myProject.getConcept(currentConceptIndex);
		//dibuja la RA
		pushStyle();//estilo de la realidad aumentada
		noStroke();
		augmentedReality.drawCamera();
		lights();
		if (augmentedReality.setTansformation()) {
			
			currentLighting = contextThread.getCurrentLighting();
			augmentedReality.setLights(currentLighting);
			
			SDprocessing.isSDcomplete(currentConcept);
			currentConcept.drawConcept(this);
			
			//for testing
			//shape(s, 0, 0);
		}
		perspective();
		popStyle();
		//myhelper.initHelpDrawer();//dibuja la ayuda inicial
		//dibuja los diferenciales semanticos
		//sdDrawer.DrawSD(currentConcept);
		
	    }
	
	public String sketchRenderer() {
		// en ecliupse size es manejado por el preprosesador... el renderizador tiene que ser especificado de esta forma
	    return P3D; 
	  }
	
	
	
	
	public static Concept getCurrentConcept(){
		return currentConcept;
	}
	
	
	
	
	
	
	
	
	

	//gesture handled by ketai...Provides gesture recognition services to a processing sketch. To receive gesture events a sketch can define the following methods:
	public void onTap(float x, float y)// - x, y location of the tap
	{
		/*
		if (x < semanticDifferentialLarge){
			sdDrawer.setScale(x, y, currentConcept);
		}
		*/
	}
	public void onDoubleTap(float x, float y)// - x,y location of double tap
	{
		
	}
	
	
	
	public void onFlick(float x, float y, float px, float py, float v)// - x,y where flick ended, px,py - where flick began, v - velocity of flick in pixels/sec 
	{
		boolean isHorizontal = false;
		boolean isRight = false;
		boolean isUp = false;
		float flickHorizontalLenght = x -px; // Negativo=Izquierda, Positivo=derecha
	    float flickVerticalLenght = y - py; //Abajo=positiva, arriba= negativo
	    
	    Log.i(tag,String.valueOf(flickVerticalLenght));
	    
	    if (abs(flickHorizontalLenght) > abs(flickVerticalLenght) ){ // Si es un flick horizontal
	    	isHorizontal = true;
	    	if(flickHorizontalLenght >0){
		    	isRight = true;
		    }
	    	currentConceptIndex = gesturesActions.change3Dmodel(currentConceptIndex, modelListlenght,  isRight); 
	    	augmentedReality.getSDdrawer().myHelper().setSwipe();
	    }
	    
	    
	   if(flickVerticalLenght <0 && !isHorizontal){
		   isUp = true;
		   boolean isSended = false;
		   if(SDprocessing.getProjectEvaluated()){//si ya se encuentra evaluado el proyecto
			   isSended = sender.sendData(myProject);
		   }
		   
		   if(isSended){
			   new AlertDialog.Builder(ArActivity.this)
		         .setTitle("Datos enviados")
		        .setMessage("Gracias!")
		          .setCancelable(false)
		          .setPositiveButton("ok", new OnClickListener() {
		          @Override
		           public void onClick(DialogInterface dialog, int which) {
	
		        	  System.exit(0);                     
		           }
		           }).create().show(); 
		   }
	   }
	   
	    
	   
	}
	
	
	
	public void onScroll(int x, int y)// - not currently used
	{
		
	}
	public void onLongPress(float x, float y)// - x, y position of long press
	{
		
	}
	public void onPinch(float x, float y, float r)// - x,y of center, r is the distance change
	{
		
	}
	public void onRotate(float x, float y, float a) //- x, y of center, a is the angle change in radians
	{
		
	}

	

	
	public boolean surfaceTouchEvent(MotionEvent event) {
		// call to keep mouseX, mouseY, etc updated
		super.surfaceTouchEvent(event);

		// forward event to class for processing
		return gesture.surfaceTouchEvent(event);
	}

}
	
	

