/*
 * Carga la configuracion de realidad aumentada del json
 */
package co.edu.eafit.services.ar;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import co.edu.eafit.activities.ArActivity;
import co.edu.eafit.activities.LogActivity;
import co.edu.eafit.conceptperception.R;
import co.edu.eafit.project.Project;


import android.content.Context;
import android.os.Environment;
import android.provider.DocumentsContract.Document;
import android.util.Log;



import processing.core.PApplet;
import ketai.ui.*;


public class LoadAR implements Iconfig{

 private ARconfig ArProjectConfig;

 private PApplet p;
 private Context ctx;
 private static String tag =  "loadConfig";
 
 private String rootDirectory;//directorio del proyecto
 private String jsonDirectory;
 private String configDirectory;

 private String appName;
 

 
 
 public LoadAR(String projectName, PApplet p){
	 	this.p = p;
	 	ctx = p.getApplicationContext();
		appName = LogActivity.appName;
		appName = "perception";// cambiar esto cuando la actividad principal sea logActivity!!!!|
		
		rootDirectory = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/"
				+ appName
				+ "/"
				+ projectName
				+ "/"
				+ projectName + ".zip" + projectName;
		
		
		
		//project directories
		jsonDirectory = rootDirectory + "/config.json";
		configDirectory = rootDirectory + "/configFiles/";
		
		
		

		Log.i(tag, "Config file: " + jsonDirectory);
		ArProjectConfig = new ARconfig();
   
 }
 
 public void computeConfiguration(){
	 loadJSONFromAsset(jsonDirectory);
	 
 }

 
 
 
 private void loadJSONFromAsset(String jsonDirectory) {
	 
	 try {
         File yourFile = new File(jsonDirectory);
         FileInputStream stream = new FileInputStream(yourFile);
         String jsonStr = null;
         try {
             FileChannel fc = stream.getChannel();
             MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

             jsonStr = Charset.defaultCharset().decode(bb).toString();
           }
           finally {
             stream.close();
           }
   
              JSONObject jsonObj = new JSONObject(jsonStr);

             // Getting data JSON Array nodes
             JSONArray data  = jsonObj.getJSONArray("ar");

             // looping through All nodes
             for (int i = 0; i < data.length(); i++) {//load config files
                 JSONObject ar = data.getJSONObject(i);

                ArProjectConfig.setMarkerSize(ar.getInt("MarkerSize"));
         		ArProjectConfig.setnumMarkers(ar.getInt("numMarkers"));
         		ArProjectConfig.setmS(ar.getDouble("mS"));
         		ArProjectConfig.setcamSizeW(ar.getInt("camSizeW"));
         		ArProjectConfig.setcamSizeH(ar.getInt("camSizeH"));
         		ArProjectConfig.setftps(ar.getInt("ftps"));
         		
         		
         		
         		ArProjectConfig.setcamPara(configDirectory + ar.getString("camPara"));
         		ArProjectConfig.setpatternPath(configDirectory + ar.getString("patternPath"));
         		
         		
               }
             
             
             
             
             

             
        } catch (Exception e) {
        e.printStackTrace();
       }

 }
 
 

 
   public ARconfig getArConfig(){
     return ArProjectConfig;
 
 }
   
   public String getProjectFolder(){
	   return rootDirectory;
	   
   }
   
   
   public AR getAR( Project myProject){
	   AR augmentedReality = new AR(p, ArProjectConfig, myProject);
	   return augmentedReality;
   }
 

}
