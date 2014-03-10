package co.edu.eafit.services.assets;

public interface IassetsManager {

	public boolean RemoveAssets(String projectName);
	public boolean DownloadAssets(String urlString);
	public boolean createObjects();
	
}
