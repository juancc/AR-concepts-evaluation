package co.edu.eafit.services.server;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Environment;
import android.util.Log;

public class Server {
	private static String serverHost;
	private int timeOut = 10*1000;
	private static final String tag = "Server";
	
	public Server(String serverHost){
		this.serverHost = serverHost;
		
	}
	
	
	//return the available projects in server
	public String[] getProjectsAvailable(String serverIP) {//return the available projects in server
		
		String tag = "getProjects";
		Log.i(tag, "Obtaining Projects...");
		
		String[] projects = {"default"};
		String newProject;
		Boolean isServer = false;
		
		
		//verificar si el servidor esta disponible
		try {
			InetAddress address = InetAddress.getByName(serverIP);
			isServer = address.isReachable(timeOut);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		if(!isServer){
			isServer = ping("10.10.50.35", 10000);
		}
		*/
		
		
		if(isServer){
			try {
				
				HttpClient client = new DefaultHttpClient();
				
			    String getURl =  serverHost + "/listProjects";
			    HttpGet get = new HttpGet(getURl);
			    HttpResponse responseGet = client.execute(get); 
			    
			    HttpEntity resEntityGet = responseGet.getEntity();
			    
			    if (resEntityGet != null) {  
			        //do something with the response
			        
			        newProject = EntityUtils.toString(resEntityGet);
			        projects = newProject.split("\\s+");
			        //Log.i(tag, newProject);
			    } 
			    
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
			if (projects.length == 0){
				projects[0] = "No projects available";
			}
			for(int i=0; i<projects.length; i++){
				Log.i(tag, "ArrayList: " + projects[i]);
			}
		
		}
		return projects;
	}
	






public Date projectServerDate(String projectName){
	Date serverFileDate = null;
	try {

		HttpURLConnection httpCon;
		URL serverURL = new URL(serverHost);

		httpCon = (HttpURLConnection) serverURL.openConnection();
		

		long date = httpCon.getLastModified();

		serverFileDate = new Date(date);

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return serverFileDate;
}


// from 
// http://stackoverflow.com/questions/6218143/how-to-send-post-request-in-json-using-httpclient
public static HttpResponse postEvaluation(String evaluation, String project) throws Exception 
{
	
	String path = serverHost + "/postPerception/" + project;

	
    //instantiates httpclient to make request
    DefaultHttpClient httpclient = new DefaultHttpClient();

    //url with the post data
    HttpPost httpost = new HttpPost(path);

 

    //passes the results to a string builder/entity
    StringEntity json = new StringEntity(evaluation);

    //sets the post request as the resulting string
    httpost.setEntity(json);
    //sets a request header so the page receving the request
    //will know what to do with it
    httpost.setHeader("Accept", "application/json");
    httpost.setHeader("Content-type", "application/json");

    //Handles what is returned from the page 
    ResponseHandler responseHandler = new BasicResponseHandler();
    return httpclient.execute(httpost, responseHandler);
}


//verificar si esta disponible el servidor
// http://stackoverflow.com/questions/1443166/android-how-to-check-if-the-server-is-available
/*
	private static boolean ping(String url, int timeout) {
	    url = url.replaceFirst("https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

	    try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        connection.setRequestMethod("HEAD");
	        int responseCode = connection.getResponseCode();
	        return (200 <= responseCode && responseCode <= 399);
	    } catch (IOException exception) {
	        return false;
	    }
	}
*/

}


