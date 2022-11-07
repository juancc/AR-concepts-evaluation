/*
 * The Median Cut algorithm for colour quantization implemented in Processing.
 *  The algorithm is implemented as described in the book "Principles of digital image processing: 
 *  Core algorithms" by Wilhelm Burger, Mark James Burge (Springer, 2009) pp 90-92.
 *  
 *  Source: https://gist.github.com/stubbedtoe/8070228
 * 
 */

package co.edu.eafit.services.contextThread;




//class to store colours for use in the ColorBoxes
public class MyColor {

	public static final int RED = 0;
	public static  final int GREEN = 1;
	public static  final int BLUE = 2;
	
	public int col;
	private  int count;
	public float[] rgb = new float[3];
	

	  //constructor takes the color as an int
	 public MyColor(int col){
	    this.col = col;
	    
	    //bitshifting faster than red(col) etc. 
	    rgb[RED]= (col >> 16) & 0xFF;
	    rgb[GREEN]=(col >> 8) & 0xFF;
	    rgb[BLUE]= col & 0xFF;
	    
	    count = 1;
	  }
	  
	  
	 public  void increment(){
		    count++;
	}
}
