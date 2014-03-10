package co.edu.eafit.services.UI;

import co.edu.eafit.conceptperception.DifferentialSemantic;

public interface Idrawer {
	public void drawLoadingAssets(String status, int percentage);
	public void drawSemanticDifferential(DifferentialSemantic projectDifSemantic);

}
