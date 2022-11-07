package co.edu.eafit.services.assets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import co.edu.eafit.activities.LogActivity;
import co.edu.eafit.conceptperception.R;
import co.edu.eafit.services.server.Server;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class ProjectManager implements IprojectManager {

	private final String tag = "ProjectManager";
	private String appName;
	private static String serverDir;//ip del servidor
	private static String serverHost;//direccion con puerto 

	
	
	//assets classes

	private downloadAssets myDownloader;
	private Server myServer;
	private FileManager myFileManager;
	
	
	public ProjectManager(){}

	public ProjectManager(String serverHost, String appName){
		
		this.serverDir = "http://"+ serverHost +":3000";
		this.serverHost = serverHost;
		this.appName = appName;

		myServer = new Server(serverDir);
		
		
	}
	
	public boolean deployAssets(String projectName, Context ctx) {
		//comienza la descarga de archivos si el proyecto no se encuentra si se encuentra retorna true
		boolean isOK = false;
		String projectDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + appName + "/" + projectName;
		myDownloader = new downloadAssets(serverDir, projectName, ctx);
		myFileManager = new FileManager(projectDirectory + "/" + projectName+ ".zip" );
		
		
		
		boolean isProjectHere = isProject(projectName, projectDirectory);
		
		if (!isProjectHere){
			Log.i(tag, "start to download");
			myDownloader.startDownload();
			
			

		}else{//ya existe el proyecto
			
			isOK = true;
		}
		//boolean isProject = myFileManager.IsProject();
		

		return isOK;
	}
	
	
	public int getDeployPercentage(){
		// Retorna el porcentaje que va.
		int downloadPercentage;
		
		if(myDownloader.downloadProgress() >= 80){
			Log.i(tag, "Extracting file...");
			myFileManager.unpackZip();
			if (myFileManager.getZipStatus()){
				downloadPercentage = 100;
				//downloadPercentage = 75;
			}else{
				downloadPercentage = 85;
			}
			
			
		}else{
			downloadPercentage = myDownloader.downloadProgress();
			
			Log.i(tag,"Downloading: " + String.valueOf(downloadPercentage) + "%");
			
		}
		return downloadPercentage;
	}
	

	
	public String[] getProjectsAvailable() {//return the available projects in server
		String[] availableProjects = myServer.getProjectsAvailable(serverHost);
		return availableProjects;
	}


	private boolean isProject(String projectName, String path){
		// verifica si el archivo zip actual del proyecto es mas antiguo que el del servidor, si no existen los directorios los crea
		boolean isProject= false;
		Date serverFileDate;
		Date folderDate;
		File projectDirectory = new File(path);
		File zipFile = new File(path + "/"+ projectName + ".zip");

		
		
		if ( zipFile.exists() ){
			
			serverFileDate = myServer.projectServerDate(projectName);
			folderDate = new Date(projectDirectory.lastModified());
			Log.i(tag, "file exist..."+ path);
			if (serverFileDate.after(folderDate)){
				isProject = false;
			}else{
				isProject = true;
			}
			
			isProject = true;
		}else{
			
			if (projectDirectory.mkdirs()){
				Log.i(tag, "Folders created:" + path);
			}
			else{
				Log.i(tag, "no work");
			}
		}

		return isProject;
	}
	
	
	
}
