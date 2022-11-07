/*
 *  The Median Cut algorithm for colour quantization implemented in Processing.
 *  The algorithm is implemented as described in the book "Principles of digital image processing: 
 *  Core algorithms" by Wilhelm Burger, Mark James Burge (Springer, 2009) pp 90-92.
 *  
 *  Source: https://gist.github.com/stubbedtoe/8070228
 */

package co.edu.eafit.services.contextThread;

import java.util.HashMap;
import java.util.Iterator;

public class ColorBox {
	
	float rmin;
	float rmax;
	float gmin;
	float gmax;
	float bmin;
	float bmax;
	HashMap cols;
	int level;
	
	
	 //constructor takes the colours contained by this box and its level of "depth"
	  public ColorBox(HashMap cols, int level){
	    this.cols = cols;
	    this.level = level;
	 
	    //3 temporary arrays used for getting the min/max of each RGB channel
	    float [] reds = new float [cols.size()];
	    float [] greens = new float [cols.size()];
	    float [] blues = new float [cols.size()];
	 
	    Iterator it = cols.values().iterator();
	    int index = 0;
	 
	    while(it.hasNext()){
	      MyColor mc = (MyColor)it.next();
	      reds[index] = mc.rgb[medianCut.RED];
	      greens[index] = mc.rgb[medianCut.GREEN];
	      blues[index] = mc.rgb[medianCut.BLUE];
	      index++;
	    }
	
	    //we need the min/max to determine which axis to split along
	    rmin = medianCut.findMinMax(reds,0);
	    rmax = medianCut.findMinMax(reds,1);
	    gmin = medianCut.findMinMax(greens,0);
	    gmax = medianCut.findMinMax(greens,1);
	    bmin = medianCut.findMinMax(blues,0);
	    bmax = medianCut.findMinMax(blues,1);
	 
	  }
	
	
	
	

}
