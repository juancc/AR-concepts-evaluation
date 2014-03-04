package com.example.conceptperception;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;



import processing.core.PApplet;
import ketai.ui.*;


public class loadConfig implements Iconfig{

 private ARconfig ArProjectConfig;
 private PApplet applet;
 private String url;
 private InputStream configFile;
 private Context ctx;
 private static String tag =  "loadConfig";
 private String text;
 
 public loadConfig(PApplet applet, String url){
   this.applet = applet;
   this.url = url;
   ctx = applet.getApplicationContext();
   //configuracion basica
   applet.size(applet.displayWidth, applet.displayHeight, applet.OPENGL);
   applet.colorMode(applet.RGB);
   applet.orientation(applet.LANDSCAPE);
   applet.imageMode(applet.CENTER);

   
   ArProjectConfig = new ARconfig();
   
 }
 
 public void computeConfiguration(){
	 downloadConfigFile();
	 setParametersFromFile();
	 
 }
 

private void downloadConfigFile(){
	   int attempts = 10;
	   int current = 0;
	   byte[] downloadedFile =  applet.loadBytes(url);
	   while (current < attempts && downloadedFile == null){
	     downloadedFile =  applet.loadBytes(url);
	     Log.i(tag, "trying to connect with server: "+current);
	     current++;
	   }
	  if (downloadedFile != null){
	   //saveBytes("configFile.xml",downloadedFile);
	   configFile = new ByteArrayInputStream(downloadedFile);
	   //configFile = applet.createInput("configFile.xml");
	   Log.i(tag, "Config file saved");
	  }
	  else{//cargar una configuracion por defecto
	    configFile = applet.createInput("defaultConfig.xml");
	    KetaiAlertDialog.popup(applet, "Conexion a internet!", "Verificar su conexion a internet, configuracion por defecto cargada");
	  }
 }
 
 
 private void setParametersFromFile(){
	 XmlPullParserFactory factory = null;
	    XmlPullParser parser = null;
	    try {
	      factory = XmlPullParserFactory.newInstance();
	      factory.setNamespaceAware(true);
	      parser = factory.newPullParser();

	      parser.setInput(configFile, null);

	      int eventType = parser.getEventType();
	      
	      while (eventType != XmlPullParser.END_DOCUMENT) {
	               String tagname = parser.getName();
	               switch (eventType) {

	               case XmlPullParser.TEXT:
	                   text = parser.getText();
	                   
	                   break;
	               case XmlPullParser.END_TAG:
	                   
	                   if (tagname.equalsIgnoreCase("config")) {
	                       Log.i(tag, "loading project configuration");
	                   } else if (tagname.equalsIgnoreCase("AR")) {
	                       Log.i(tag,"loading AR configuration");
	                   } 
	                   if (tagname.equalsIgnoreCase("MarkerSize")) {
	                       ArProjectConfig.setMarkerSize(Integer.parseInt(text));
	                       Log.i(tag, "markerSize: "+ text);
	                   } else if (tagname.equalsIgnoreCase("numMarkers")) {
	                       ArProjectConfig.setnumMarkers(Integer.parseInt(text));
	                   } else if (tagname.equalsIgnoreCase("mS")) {
	                       ArProjectConfig.setmS(Float.valueOf(text));
	                   } else if (tagname.equalsIgnoreCase("patternPath")) {
	                       ArProjectConfig.setpatternPath(text);
	                   } else if (tagname.equalsIgnoreCase("camPara")) {
	                       ArProjectConfig.setcamPara(text);
	                   } else if (tagname.equalsIgnoreCase("camSizeW")) {
	                       ArProjectConfig.setcamSizeW(Integer.parseInt(text));
	                   } else if (tagname.equalsIgnoreCase("camSizeH")) {
	                       ArProjectConfig.setcamSizeH(Integer.parseInt(text));
	                   } else if (tagname.equalsIgnoreCase("ftps")) {
	                       ArProjectConfig.setftps(Integer.parseInt(text));
	                   }
	                   
	                   break;

	               default:
	                   break;
	               }
	               eventType = parser.next();
	           } 
	      
	    }
	    catch (XmlPullParserException e) {
	      e.printStackTrace();
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }
   }
   
 

 
   public ARconfig getArConfig(){
     return ArProjectConfig;
 
 }
 

}
