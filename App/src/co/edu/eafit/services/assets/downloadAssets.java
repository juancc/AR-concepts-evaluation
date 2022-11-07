package co.edu.eafit.services.assets;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("NewApi")
public class downloadAssets {
	private Context ctx;
	private String projectName;
	private DownloadManager downloadmanager;
	private long lastDownload=-1L;
	private URL url;
	private String tag = "downloadAssets";
	private long reference;
	private String path;
	private int totalDownload;
	
	public downloadAssets(String urlString, String projectName , Context ctx){
		try {
			String urlTest = urlString + "/projects/" + projectName;
			url = new URL(urlTest);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.ctx = ctx;
		this.projectName = projectName;
		String servicestring = Context.DOWNLOAD_SERVICE;
		downloadmanager = (DownloadManager) ctx.getSystemService(servicestring);
		path = "/perception" + "/" + projectName + ".zip";
		
	}
	
	@SuppressLint("NewApi")
	public void startDownload() {

		Uri uri = Uri.parse(url.toString());
		DownloadManager.Request request = new Request(uri);
		reference = downloadmanager.enqueue(request);
		

		request.setAllowedNetworkTypes(
				DownloadManager.Request.NETWORK_WIFI
						| DownloadManager.Request.NETWORK_MOBILE)
				.setAllowedOverRoaming(false)
				.setTitle("Perception test")
				.setDescription("Download Project")
				.setDestinationInExternalPublicDir(
						"/perception" + "/" + projectName, projectName + ".zip");

		downloadmanager.enqueue(request);
		
		
	}
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public boolean queryStatus(){
		
		Cursor c= downloadmanager.query(new DownloadManager.Query().setFilterById(lastDownload));
		
		 if (c==null) {
		      Toast.makeText(ctx, "Download not found!", Toast.LENGTH_LONG).show();
		    }
		    else {
		      c.moveToFirst();
		      
		      Log.d(getClass().getName(), "COLUMN_ID: "+
		            c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
		      Log.d(getClass().getName(), "COLUMN_BYTES_DOWNLOADED_SO_FAR: "+
		            c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
		      Log.d(getClass().getName(), "COLUMN_LAST_MODIFIED_TIMESTAMP: "+
		            c.getLong(c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)));
		      Log.d(getClass().getName(), "COLUMN_LOCAL_URI: "+
		            c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
		      Log.d(getClass().getName(), "COLUMN_STATUS: "+
		            c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)));
		      Log.d(getClass().getName(), "COLUMN_REASON: "+
		            c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON)));
		      
		      Toast.makeText(ctx, statusMessage(c), Toast.LENGTH_LONG).show();
		    }

		return false;
	}
	
	
	  private String statusMessage(Cursor c) {
		    String msg="???";
		    
		    switch(c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
		      case DownloadManager.STATUS_FAILED:
		        msg="failed";
		        break;
		      
		      case DownloadManager.STATUS_PAUSED:
		        msg="paused";
		        break;
		      
		      case DownloadManager.STATUS_PENDING:
		        msg="pending";
		        break;
		      
		      case DownloadManager.STATUS_RUNNING:
		        msg="progress";
		        break;
		      
		      case DownloadManager.STATUS_SUCCESSFUL:
		        msg="complete";
		        break;
		      
		      default:
		        msg="nowhere";
		        break;
		    }
		    Log.i(tag, "msg");
		    return(msg);
		  }
	  
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@SuppressLint("NewApi")
	  public String getStatus(){
		  // retorna el estatus de la descarga en un string
		  String status = "???";
		  Cursor c= downloadmanager.query(new DownloadManager.Query().setFilterById(lastDownload));
		  
		  if (c!= null){
			  
			  c.moveToFirst();
			  status = statusMessage(c);
			  
		  }else{
			  status = "noFound";
			  Log.i(tag, "NF");
		  }
		  
		  return status;
	  }
		
		
	public int downloadProgress(){


		DownloadManager.Query q = new DownloadManager.Query();
		q.setFilterById(reference);
		Cursor cursor = downloadmanager.query(q);
		cursor.moveToFirst();
		int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
		totalDownload = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
		
		if(totalDownload < 0){
			totalDownload = -totalDownload;
		}
		
		
		int percentageDownloaded = (int) (bytes_downloaded * 100L / totalDownload);
		
		
		
		cursor.close();

		
		
		//Log.i(tag, "total: "+ String.valueOf(totalDownload)+"actual: "+ String.valueOf(bytes_downloaded));
		//Log.i(tag, "downloaded: " + String.valueOf(percentageDownloaded));
		
		return percentageDownloaded;
	}
	
	
	public boolean directDownload(){
		boolean downloadComplete = false;
		int count;
        try {

            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();
 
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
 
            // Output stream to write file
            OutputStream output = new FileOutputStream(path);
 
            byte data[] = new byte[1024];
 
            long total = 0;
 
            while ((count = input.read(data)) != -1) {
                total += count;
                totalDownload = (int)((total*80)/lenghtOfFile);
                // writing data to file
                output.write(data, 0, count);
            }
            
            
            // flushing output
            output.flush();
 
            // closing streams
            output.close();
            input.close();
            
            downloadComplete = true;
 
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            downloadComplete = false;
        }
		
		return downloadComplete;
	}
	
	public int getDownloadPercentage(){
		
		return totalDownload;
	}
	
	
	
	
}
