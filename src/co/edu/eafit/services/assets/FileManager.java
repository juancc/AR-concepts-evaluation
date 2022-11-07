package co.edu.eafit.services.assets;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.os.Environment;
import android.util.Log;

public class FileManager {
	private String path;
	
	private static boolean isUnziped;
	private String tag = "FileManager";
	//private String serverHost;
	//private URL urlProject; 
	
	public FileManager(String projectDirectory){
		this.path = projectDirectory;

		
	}
	
	
	
	public boolean unpackZip(){
		String filename;
		InputStream is;
	    ZipInputStream zis;
	    Log.i(tag, path);
	    
	    try 
	     {
	         is = new FileInputStream(path);
	         zis = new ZipInputStream(new BufferedInputStream(is));          
	         ZipEntry ze;
	         
	         
	         byte[] buffer = new byte[1024];
	         int count;

	         while ((ze = zis.getNextEntry()) != null) 
	         {
	             // zapis do souboru
	             filename = ze.getName();
	             
	             // Need to create directories if not exists, or
	             // it will generate an Exception...
	             if (ze.isDirectory()) {
	                File fmd = new File(path + filename);

	                fmd.mkdirs();
	                continue;
	             }

	             FileOutputStream fout = new FileOutputStream(path + filename);

	             // cteni zipu a zapis
	             while ((count = zis.read(buffer)) != -1) 
	             {
	                 fout.write(buffer, 0, count);             
	             }

	             fout.close();               
	             zis.closeEntry();
	             isUnziped = true;
	         }

	         zis.close();
	     } 
	     catch(IOException e)
	     {
	         e.printStackTrace();
	         return false;
	     }

	    return isUnziped;
	     
	}
	
	
	
	
	public boolean getZipStatus(){
		
		return isUnziped;
	}
	
	
	/*
	public boolean RemoveAssets(String projectName){
		return false;
		
	}
	

	
	public Date projectDate(String projectRequested){
		File projectDirectory = new File(Environment.getExternalStorageDirectory()
				+ appFolder + "/" + projectRequested);
		
		
		Date folderDate = new Date(projectDirectory.lastModified());
		
		return folderDate;
		
		
	}

	
	
	
	boolean IsProject(String projectRequested) {
		//verifica si existe el directorio y si existe lo compara con la fecha de modificacion del archivo en el servidor, si es mas antigua  retorna falso
		boolean isProject = false;
		File projectDirectory = new File(Environment.getExternalStorageDirectory()
				+ appFolder + "/" + projectRequested);

		if (projectDirectory.exists()) {


		}
		else
			isProject = false;
		
		
		return isProject;
	}
	 */
}
