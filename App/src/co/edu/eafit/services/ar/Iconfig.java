package co.edu.eafit.services.ar;

import co.edu.eafit.project.Project;




public interface Iconfig {
	// Clase que carga la configuracion cel programa
	  public ARconfig getArConfig();
	  public void computeConfiguration();
	  public String getProjectFolder();
	  public AR getAR(Project myProject);
	  

}
