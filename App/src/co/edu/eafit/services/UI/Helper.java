package co.edu.eafit.services.UI;

import processing.core.PApplet;

public class Helper {
	private PApplet p;
	private static int swipeCounter=0;
	
	public Helper(PApplet p){
		this.p = p;
	}
	
	public void initHelpDrawer(){
		if(swipeCounter < 3){
			p.pushStyle();
			p.hint(p.DISABLE_DEPTH_TEST);
			p.noLights();
			p.noStroke();
			p.camera();
			
			p.textSize(28);
			p.text("Deslice hacia la derecha o izquierda", p.width/3, 2*p.height/3);
			
			p.popStyle();
			p.hint(p.ENABLE_DEPTH_TEST);
		}
		
	}
	
	public static void setSwipe(){
		swipeCounter++;
	}
}
