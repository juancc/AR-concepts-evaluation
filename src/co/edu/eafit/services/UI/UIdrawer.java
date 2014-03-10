package co.edu.eafit.services.UI;

import processing.core.PApplet;

public class UIdrawer {
	private PApplet applet;
	public UIdrawer(PApplet applet){
		this.applet = applet;
		
		// configuracion del drawer
		applet.size(applet.displayWidth, applet.displayHeight, applet.OPENGL);
		applet.colorMode(applet.RGB);
		applet.orientation(applet.LANDSCAPE);
		applet.imageMode(applet.CENTER);	
	}
	
	
	
}
