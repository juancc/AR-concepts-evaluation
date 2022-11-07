/*
 * The Median Cut algorithm for colour quantization implemented in Processing.
 *  The algorithm is implemented as described in the book "Principles of digital image processing: 
 *  Core algorithms" by Wilhelm Burger, Mark James Burge (Springer, 2009) pp 90-92.
 *  
 *  Source: https://gist.github.com/stubbedtoe/8070228
 */


package co.edu.eafit.services.contextThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.util.Log;

import processing.core.PApplet;
import processing.core.PImage;

public class medianCut {
	
	public static final int RED = 0;
	public static  final int GREEN = 1;
	public static  final int BLUE = 2;
	
	private static final String tag = "medianCut";
	private static PApplet p;
	
	
	
	
	
	//takes the original image and amount of colours required in the quantized image.
	//returns the quantized image.
	public static PImage applyMedianCut(PImage orig, int maxColors){
	 
	  int[]repCols = findRepresentativeColors(orig,maxColors);
	  return quantizeImage(orig, repCols); 
	 
	}
	
	
	public int[] getRepresentativeColors(PImage orig, int maxColors){
		int [] ColorsPalette = findRepresentativeColors(orig,maxColors);
		return ColorsPalette;
	}
	
	
	
	//takes the original image and an array of new colours of size maxColors.
	//returns the quantized image.
	private static PImage quantizeImage(PImage img, int [] newCols){
	 
	  //the new image should be the same size as the original

	  PImage newImg = p.createImage(img.width,img.height,p.RGB);
	 
	  img.loadPixels();
	  newImg.loadPixels();
	  //find the closest colour in the array to the original pixel's colour
	  for(int i=0; i<img.pixels.length; i++){
	    int index = 0;
	    float closest = distanceToRGB(img.pixels[i],newCols[0]);
	    for(int j=1;j<newCols.length;j++){
	      if(closest > distanceToRGB(img.pixels[i],newCols[j])){
	        closest = distanceToRGB(img.pixels[i],newCols[j]);
	        index = j;
	      }
	    }
	    //set the new image's pixel to the closest colour in the array
	    newImg.pixels[i] = newCols[index];
	  }
	  img.updatePixels();
	  newImg.updatePixels();
	  return newImg;
	}
	
	
	
	//the main loop to subdivide the large colour space into the required amount 
	//of smaller boxes. return the array of the average colour for each of these boxes.
	public static int [] findRepresentativeColors(PImage orig, int maxColors){
	 
	  //get a reference to each colour in the original image
	  HashMap origCols = findOriginalColors(orig);

	  if(origCols.size() <= maxColors){
	 
	    //num of colours is less than or equal to the max num in the orig image
	    //so simply return the colour in an int array
	 
	    int[]toReturn = new int[origCols.size()];
	    Iterator it = origCols.values().iterator();
	    int index = 0;
	    while(it.hasNext()){
	      MyColor mc = (MyColor)it.next();
	      toReturn[index] = mc.col;
	      index++;
	    }
	    return toReturn;
	 
	  }else{
	 
	    //otherwise subdivide the box of colours untl the required number 
	    //has been reached
	 
	    ArrayList colorBoxes = new ArrayList(); //where the boxes will be stored
	    ColorBox first = new ColorBox(origCols,0); //the largest box (level 0)
	    colorBoxes.add(first);
	    int k = 1; //we have one box
	    boolean done = false;
	 
	    while(k<maxColors && !done){
	 
	      ColorBox next = findBoxToSplit(colorBoxes);
	      if(next != null){
	 
	        ColorBox [] boxes = splitBox(next);
	 
	        if(colorBoxes.remove(next)){} //finds and removes an element in one
	        //replaced with the two smaller boxes that make it up 
	        colorBoxes.add(boxes[0]);
	        colorBoxes.add(boxes[1]);  
	        k++; //we have one more box
	 
	      }else{
	        done = true;
	      }
	    }
	 
	    //get the average colour from each of the boxes in the arraylist
	    int [] avgCols = new int [colorBoxes.size()];
	    for(int i=0; i<avgCols.length; i++){
	      ColorBox cb = (ColorBox)colorBoxes.get(i);
	      avgCols[i] = averageColor(cb);
	    }
	 
	    return avgCols;
	 
	  }
	}
	
	
	
	
	//takes the list of all candidate boxes and returns the one to be split next.
	private static ColorBox findBoxToSplit(ArrayList listOfBoxes){
	 
	  ArrayList canBeSplit = new ArrayList();
	 
	  for(int i = 0; i<listOfBoxes.size(); i++){
	    ColorBox cb = (ColorBox)listOfBoxes.get(i);
	    //only boxes containing more than one colour can be split 
	    if(cb.cols.size() > 1){
	      canBeSplit.add(cb);
	    }  
	  }
	 
	  if(canBeSplit.size() == 0){
	    return null; //a null will trigger the end of the subdividing loop
	  }else{
	 
	    //use the 'level' of each box to ensure they are divided in the correct order.
	    //the box with the lowest level is returned.
	 
	    ColorBox minBox = (ColorBox)canBeSplit.get(0);
	    int minLevel = minBox.level;
	 
	    for(int i=1; i<canBeSplit.size(); i++){
	      ColorBox test = (ColorBox)canBeSplit.get(i);
	      if(minLevel > test.level){
	        minLevel = test.level;
	        minBox = test;
	      }
	    }
	 
	    return minBox;
	 
	  }
	 
	}
	
	
	
	
	//divide a box along its longest RGB axis to create 2 smaller boxes and return these
	private static ColorBox [] splitBox(ColorBox bx){
	 
	  int m = bx.level; //store the current 'level'
	  int d = findMaxDimension(bx); //the dimension to split along
	 
	  //get the median only counting along the longest RGB dimension
	  HashMap cols = bx.cols;
	  Iterator it = cols.values().iterator();
	  float c = (float) 0.0;
	  while(it.hasNext()){
	    MyColor mc = (MyColor)it.next();
	    c += mc.rgb[d];  
	  }
	 
	  float median = c/cols.size();
	 
	  //the two Hashmaps to contain all the colours in the original box
	  HashMap left = new HashMap();
	  HashMap right = new HashMap();
	 
	  Iterator itr = cols.values().iterator();
	  while(itr.hasNext()){
	    MyColor mc = (MyColor)itr.next();
	    //putting each colour in the appropriate box
	    if(mc.rgb[d] <= median){
	      left.put(mc.col,mc);
	    }else{
	      right.put(mc.col,mc);
	    }
	  }
	 
	  ColorBox [] toReturn = new ColorBox [2];
	  toReturn[0] = new ColorBox(left, m+1); //the 'level' has increased 
	  toReturn[1] = new ColorBox(right, m+1);
	 
	  return toReturn;
	 
	}
	
	
	
	//the method to find and return the longest axis of the box as the one to divide along.
	static int findMaxDimension(ColorBox bx){
	 
	  float [] dims = new float [3];
	  //the length of each is measured as the (max value - min value)
	  dims[0] = bx.rmax - bx.rmin;
	  dims[1] = bx.gmax - bx.gmin;
	  dims[2] = bx.bmax - bx.bmin;
	 
	  float sizeMax = findMinMax(dims,1);
	  if(sizeMax == dims[0]){
	    return RED;
	  }else if(sizeMax == dims[1]){
	    return GREEN;
	  }else{
	    return BLUE;
	  }
	}
	
	
	
	//get the average colour of all the colours contained by the given box
	static int averageColor(ColorBox bx){
	 
	  HashMap cols = bx.cols;
	  Iterator it = cols.values().iterator();
	  float [] rgb = {(float) 0.0,(float) 0.0,(float) 0.0}; //start at zero
	  while(it.hasNext()){
	    MyColor mc = (MyColor)it.next();
	    for(int i=0; i<3; i++){
	      rgb[i] += mc.rgb[i]; //sum of each channel stored separately
	    }
	  }
	  float avgRed = rgb[RED]/cols.size();
	  float avgGreen = rgb[GREEN]/cols.size();
	  float avgBlue = rgb[BLUE]/cols.size();
	 
	  return p.color(avgRed,avgGreen,avgBlue);
	}
	
	
	
	/**
	  Some helper functions
	*/
	 
	//get the distance between two colours in Euclidian space
	private static float distanceToRGB(int col1, int col2){
	  float redDiff = p.abs(p.red(col1)-p.red(col2));
	  float greenDiff = p.abs(p.green(col1)-p.green(col2));
	  float blueDiff = p.abs(p.blue(col1)-p.blue(col2));
	  return (float) ((redDiff+greenDiff+blueDiff)/3.0);
	}
	
	
	
	//called at the very start to determine the colours in the original image
	private static HashMap findOriginalColors(PImage orig){
	 
	  //entries in the hashmap are stored in (key,value) pairs
	  //key = int colour , value = MyColor colorObject 
	  HashMap toReturn = new HashMap();
	  orig.loadPixels();
	  
	  for(int i = 0; i<orig.pixels.length; i++){
	    //we've already come across this colour
	    if(toReturn.containsKey(orig.pixels[i])){ 
	    	 
	      MyColor temp = (MyColor)toReturn.get(orig.pixels[i]);
	      temp.increment(); //increase its 'count' value
	      
	    }else{
	      MyColor toAdd = new MyColor(orig.pixels[i]);
	      toReturn.put(orig.pixels[i],toAdd);
	    }
	  }
	  orig.updatePixels();
	 
	  return toReturn;
	}
	
	
	
	
	
	//get the minimum OR maximum from an array of floats
	public static float findMinMax(float [] f, int k){
	  if(f.length>0){
	    float m = f[0];
	    for(int i =1; i<f.length; i++){
	      //if k is 0 the minimum is required. Otherwise return the maximum. 
	      if(k==0){
	        m = p.min(m,f[i]);
	      }else{
	        m = p.max(m,f[i]);
	      }
	    } 
	    return m;
	  }else{
	    return (float) 0.0;
	  }
	}
	
}
