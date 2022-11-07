package co.edu.eafit.services.contextThread;

import java.util.HashMap;
import java.util.List;

import android.util.Log;

import processing.core.PImage;

public class colors {

	private final static String tag = "colors";

	// called at the very start to determine the colours in the original image
	// Source: https://gist.github.com/stubbedtoe/8070228
	/*
	public static HashMap findOriginalColors(PImage frame, HashMap contextColors) {
		// agrega los coloresdiferentes al HashMap
		Log.i(tag, "Saving scene colors");
		// entries in the hashmap are stored in (key,value) pairs
		// key = int colour , value = MyColor colorObject
		// HashMap toReturn = new HashMap();

		frame.loadPixels();
		for (int i = 0; i < frame.pixels.length; i++) {
			// we've already come across this colour
			// Log.i(tag, String.valueOf(frame.pixels[i]));
			if (contextColors.containsKey(frame.pixels[i])) {
				try {
					MyColor temp = (MyColor) contextColors.get(frame.pixels[i]);
					temp.increment(); // increase its 'count' value
				} catch (NullPointerException e) {
					// do something other
				}

			} else {
				// Log.i(tag, "Adding "+ String.valueOf(frame.pixels[i]));
				MyColor toAdd = new MyColor(frame.pixels[i]);
				contextColors.put(frame.pixels[i], toAdd);
			}
		}
		// frame.updatePixels();

		return contextColors;
	}
	*/
	/*
	 //consume mucha memoria este metodo
	//agrega nuevos colores a la lista
	public static List<Integer[]> addColor(PImage frame, List<Integer[]> contextColors){
		Log.i(tag, "Saving scene colors");
		
		
		frame.loadPixels();
		if(contextColors.size() == 0){
			Integer[] newColor = {frame.pixels[0], 0};
			contextColors.add( newColor );
		}
		for (int i=0; i < frame.pixels.length; i++) {
			// we've already come across this colour
			
			
			for(int j=0; j<contextColors.size(); j++){
				//si el color ya existe
				try{
				if(frame.pixels[i] == contextColors.get(j)[0]){
					//Log.i(tag, String.valueOf(frame.pixels[i]));
					contextColors.get(j)[1]++;
					
				} else {
					
					Integer[] newColor = {frame.pixels[i], 1};
					contextColors.add( newColor );
				}
				}catch(NullPointerException e){
					
				}
			}
		}
		
		return contextColors;
	}
	*/
	public static HashMap addColor(PImage frame, HashMap<Integer, Integer> contextColors) {
		// agrega los coloresdiferentes al HashMap
		Log.i(tag, "Saving scene colors");
		// entries in the hashmap are stored in (key,value) pairs
		// key = int colour , value = color of processing
		// HashMap toReturn = new HashMap();
		
			frame.loadPixels();
		
			for (int i = 0; i < frame.pixels.length; i++) {
				// we've already come across this colour
				// Log.i(tag, String.valueOf(frame.pixels[i]));
				if(contextColors.size() < 1500){// limitar el tamaño, for testing
					
					int pixel = frame.pixels[i];
					if (contextColors.containsKey(pixel)) {
						try {
							//Log.i(tag, String.valueOf(pixel));
							contextColors.put(pixel, contextColors.get(pixel) +  1);
						} catch (NullPointerException e) {
							// do something other
						}
		
					} else {
		
						contextColors.put(frame.pixels[i], 1);
					}
				}
			
		}
		// frame.updatePixels();

		return contextColors;
	}
	
}
