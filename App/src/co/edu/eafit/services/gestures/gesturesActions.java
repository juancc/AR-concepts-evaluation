package co.edu.eafit.services.gestures;


import co.edu.eafit.services.UI.Helper;
import android.util.Log;
import android.view.MotionEvent;
import processing.core.PApplet;

import ketai.ui.KetaiGesture;


public class gesturesActions{
	private final static String tag = "gesturesHandler";
	
	
	
	public gesturesActions() {

	}

	
	
	public static int change3Dmodel(int currentModel, int ModelListlenght, boolean isRight){
		
		if(!isRight){
			if(currentModel < ModelListlenght-1){
				currentModel += 1;
			}
			else {
				currentModel = 0;
			}
		}else{
			if(currentModel > 0){
				currentModel -= 1;
			}
			else if(currentModel == 0){
				currentModel = ModelListlenght-1;
			}
		}
		
		Helper.setSwipe();//un swipe fue hecho
		
		Log.i(tag, "3dModel number: "+ currentModel);
		return currentModel;
	}
	
	


}
