package co.edu.eafit.services.assets;

import java.net.MalformedURLException;
import java.net.URL;


public class AssetsManager implements IassetsManager{
	public AssetsManager(){
		
	}

	
	
	public boolean DownloadAssets(String urlString) {
		try {
			URL url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean RemoveAssets(String projectName){
		return false;
		
	}
	


	private boolean createFolders() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean createObjects() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

}
