package com.example.conceptperception;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.provider.DocumentsContract.Document;
import android.util.Log;



import processing.core.PApplet;
import ketai.ui.*;


public class loadConfig implements Iconfig{

 private ARconfig ArProjectConfig;
 private PApplet applet;
 private String urlString;
 private InputStream configFile;
 private Context ctx;
 private static String tag =  "loadConfig";


 private XmlPullParserFactory xmlFactoryObject;
 
 private URL urlTest;
 
 public loadConfig(PApplet applet, String url){
   this.applet = applet;
   this.urlString = url;
   
  
   
   ctx = applet.getApplicationContext();
   //configuracion basica
   applet.size(applet.displayWidth, applet.displayHeight, applet.OPENGL);
   applet.colorMode(applet.RGB);
   applet.orientation(applet.LANDSCAPE);
   applet.imageMode(applet.CENTER);

   
   ArProjectConfig = new ARconfig();
   
 }
 
 public void computeConfiguration(){
	 InputStream stream = fetchXML();
	 setParametersFromFile(stream);
	 
 }

 
 
 
	private void setParametersFromFile(InputStream stream) {
		int eventType;
		String text = null;
		try {
		xmlFactoryObject = XmlPullParserFactory.newInstance();

		XmlPullParser myParser = xmlFactoryObject.newPullParser();

		myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		myParser.setInput(stream, null);
		
			eventType = myParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tagname = myParser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:{
					break;
				}
				case XmlPullParser.TEXT:{
					text = myParser.getText();
					System.out.println(text);
					break;
				}
				case XmlPullParser.END_TAG:{
					if (tagname.equalsIgnoreCase("config")) {
						Log.i(tag, "loading project configuration");
					} else if (tagname.equalsIgnoreCase("AR")) {
						Log.i(tag, "loading AR configuration");
					} else if (tagname.equalsIgnoreCase("MarkerSize")) {
						ArProjectConfig.setMarkerSize(Integer.parseInt(text));
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
				}
				default:
					break;
				}
				
				eventType = myParser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
 
	private InputStream fetchXML() {
		InputStream stream;
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			stream = conn.getInputStream();
		} catch (Exception e) {
			stream = applet.createInput("defaultConfig.xml");
			Log.e(tag, "Default configuration loaded");
		}
		return stream;

	}
 
 
 
 

 
   public ARconfig getArConfig(){
     return ArProjectConfig;
 
 }
 

}
