package co.edu.eafit.services.assets;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;

public interface IprojectManager {

	public boolean deployAssets(String projectName, Context ctx);
	public String[] getProjectsAvailable();
	public int getDeployPercentage();
	

	
}
