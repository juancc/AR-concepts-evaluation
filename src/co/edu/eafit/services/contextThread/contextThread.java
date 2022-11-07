package co.edu.eafit.services.contextThread;

import java.util.HashMap;
import java.util.List;

import ketai.camera.KetaiCamera;
import processing.core.PApplet;
import processing.core.PVector;
import android.util.Log;
import co.edu.eafit.activities.ArActivity;
import co.edu.eafit.project.Concept;
import co.edu.eafit.project.Project;
import co.edu.eafit.services.ar.AR;

public class contextThread extends Thread {
	private boolean running;// si el thread esta corriendo
	private static final String tag = "contextThread";
	private int wait; // cuantos milisegundos debe esperar entre ejecuciones
	private String id; // nombre del thread
	private static int currentLighting;
	private Project myProject;


	private PApplet p;

	 //constructor crea el thread
	public contextThread(int w, String s, PApplet p, Project myProject) {
		this.p = p;
		this.myProject = myProject;
		wait = w;
		running = false;
		id = s;


	}



	public void start() {

		running = true;
		// Print messages
		Log.i(tag, "Calculating ambient Light (will execute every " + wait
				+ " milliseconds.)");
		// Do whatever start does in Thread, don't forget this!
		super.start();
	}

	public void run() {

		while (running) {
			// calculo de la luz actual
			Concept currentConcept = ArActivity.getCurrentConcept();
			currentLighting = lighting.calcLighting(p);
			//Log.i(tag, String.valueOf(currentLighting));
			Project.addLighting(currentLighting);
			
			
			//colores del escenario
			
			 HashMap<Integer, Integer> newContextColors = colors.addColor(AR.getCam(), myProject.getContextColors());
			myProject.setContextColors(newContextColors);
			
			
			try {
				sleep((long) (wait));
			} catch (Exception e) {
			}
		}

		System.out.println(id + " thread is done!"); // The thread is done when
														// we get to the end of
														// run()
	}

	public static int getCurrentLighting() {
		return currentLighting;
	}
}
