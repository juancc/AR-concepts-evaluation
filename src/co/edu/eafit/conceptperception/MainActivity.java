package co.edu.eafit.conceptperception;

import co.edu.eafit.services.ar.AR;
import co.edu.eafit.services.ar.ARconfig;
import co.edu.eafit.services.config.Iconfig;
import co.edu.eafit.services.config.loadConfig;
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

public class MainActivity extends PApplet{
	public static void main (String args[]){
		PApplet.main(new String[]{"-present", "something.whatever"});	
	}
	String tag = "mainActivity";
	//KetaiCamera cam;
	Iconfig Configuration;
	AR augmentedReality;
	ARconfig arConfiguration;
	private static final String domain = "juancc.myftp.org/projects";
	boolean assetsReady;
	 
	@Override
	public void setup() {
		
		//falta objeto que carga las direcciones
		String urlConfig = "https://dl.dropboxusercontent.com/s/dlx08szxy9u244g/config.xml";
	
		
		
		Configuration = new loadConfig(this, urlConfig);// objeto que crea la configuracion de todos los objetos
		Configuration.computeConfiguration();
		arConfiguration = Configuration.getArConfig();// configuracion del AR
		augmentedReality = new AR(this, arConfiguration);
	
	    
		
	}
	
	@Override
	public void draw() {
		augmentedReality.drawCamera();
		
	    }
	
	
	
	
	
}
	
	

