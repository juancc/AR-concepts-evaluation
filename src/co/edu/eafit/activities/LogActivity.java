package co.edu.eafit.activities;

import java.io.IOException;

import java.net.MalformedURLException;


import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import co.edu.eafit.conceptperception.R;

import co.edu.eafit.services.assets.ProjectManager;
import co.edu.eafit.services.assets.IprojectManager;
import co.edu.eafit.services.dataValidation.validationMethods;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.text.Editable;
import android.text.TextWatcher;

public class LogActivity extends Activity {
	
	private String serverHost;
	private Boolean isServer;
	private String tag = "LogActivity";
	private String emailValid, ageValid, socialStrataValid, genderValid, professionValid, projectToEval  = null;
	
	public final static String PROJECT_EXTRA = "co.edu.eafit.LogActivity.project";
	public final static String EMAIL_EXTRA = "co.edu.eafit.LogActivity.email";
	public final static String AGE_EXTRA = "co.edu.eafit.LogActivity.age";
	public final static String PROFESSION_EXTRA = "co.edu.eafit.LogActivity.profesion";
	public final static String SOCIAL_EXTRA = "co.edu.eafit.LogActivity.social";
	public final static String GENDER_EXTRA = "co.edu.eafit.LogActivity.gender";
	//public final static String USER_EXTRA = "co.edu.eafit.LogActivity.project";
	
	
	public static String appName;
	private String[] listProjects;//list of available projects in server
	
	private EditText email, age, profession;
	private ProgressDialog barProgressDialog;
	private Handler updateBarHandler;
	private Spinner socialStrat, gender, selectedProject;
	private Hashtable<String, String> userData = new Hashtable();
	private  IprojectManager myProjectManager;
	

	private int AssetsProgress;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		appName = getResources().getString(R.string.app_name);
		serverHost = getResources().getString(R.string.serverHost);
		
		myProjectManager = new ProjectManager(serverHost, appName);
		
		
		updateBarHandler = new Handler();
		
			
		
		listProjects = myProjectManager.getProjectsAvailable();//List of available projects in server
		
		if(listProjects.length == 1 && listProjects[0] == "default" ){//si no hay conexion con el servidor
			alert(this);
			isServer = false;
		}else{
			isServer = true;
		}
		
		
		populateLists(listProjects);// populate elements of the spinners: social stratification, gender and profession
		
		//validate data from https://sites.google.com/site/khetiyachintan/sample-example/edit-text-validation-example
		email = (EditText) findViewById(R.id.email);
		age = (EditText) findViewById(R.id.age);
		socialStrat = (Spinner) findViewById(R.id.socialStrata_spinner);
		gender = (Spinner) findViewById(R.id.gender_spinner);
		profession = (EditText) findViewById(R.id.autocomplete_profession);
		selectedProject = (Spinner) findViewById(R.id.projects_spin);
		
		
		
		email.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String emailError =  getResources().getString(R.string.emailError);
				emailValid = validationMethods.Is_Valid_Email(email, emailError);
			}
		});
		
		
		
		
		age.addTextChangedListener(new TextWatcher() {

			@Override
		public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}
			@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				// minimum 1 , max length  10
				String ageError =  getResources().getString(R.string.ageError);
				ageValid = validationMethods.age_validation(90, age, ageError);
			}
		});
		
		socialStrat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			String socialStrataError = getResources().getString(R.string.socialStrataError);
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { 
		    	socialStrataValid = validationMethods.socialStrata_validation(socialStrat);
		    	if (socialStrataValid==null){
		    		Toast.makeText(LogActivity.this, socialStrataError, Toast.LENGTH_SHORT).show();
		    	}
		    } 

		    public void onNothingSelected(AdapterView<?> adapterView) {
		        return;
		    } 
		}); 
		
		gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			String genderError = getResources().getString(R.string.genderError);
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { 
		    	genderValid = validationMethods.gender_validation(gender);
		    	if (genderValid ==null){
		    		Toast.makeText(LogActivity.this, genderError, Toast.LENGTH_SHORT).show();
		    	}
		    } 

		    public void onNothingSelected(AdapterView<?> adapterView) {
		        return;
		    } 
		}); 
		
		
		profession.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String professionError =  getResources().getString(R.string.professionError);
				professionValid = validationMethods.profession_validation(profession, professionError);
			}
		});
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log, menu);
		return true;
	}
	
	
	
	
	
	
	public boolean populateLists(String[] listProjects) {
		// populate Spinner from http://developer.android.com/guide/topics/ui/controls/text.html
		
		//Social stratification
		Spinner SocialStrata_spinner = (Spinner) findViewById(R.id.socialStrata_spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.socialStrata_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		SocialStrata_spinner.setAdapter(adapter);
		
		
		// gender
		Spinner gender_spinner = (Spinner) findViewById(R.id.gender_spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.gender_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		gender_spinner.setAdapter(adapter2);

		// profession
		// Get a reference to the AutoCompleteTextView in the layout
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_profession);
		// Get the string array
		String[] countries = getResources().getStringArray(R.array.profession_array);
		// Create the adapter and set it to the AutoCompleteTextView 
		ArrayAdapter<String> adapter3 = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
		textView.setAdapter(adapter3);
		
		
		// list of projects spinner
				Spinner projects_spinner = (Spinner) findViewById(R.id.projects_spin);
				// Create an ArrayAdapter using the string array and a default spinner
				// layout
				ArrayAdapter<String> adapter4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listProjects);
				// Specify the layout to use when the list of choices appears
				adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Apply the adapter to the spinner
				projects_spinner.setAdapter(adapter4);
		
		return true;
	}
	
	
	
	
	/*
	public void saveData(View view){
		boolean isDataOK = true;
		projectToEval  = selectedProject.getItemAtPosition(selectedProject.getSelectedItemPosition()).toString();
		
		
		Hashtable<String, String> userData = new Hashtable();
		try{
		userData.put("email", emailValid);
		userData.put("age", ageValid);
		userData.put("social", socialStrataValid);
		userData.put("gender", genderValid);
		userData.put("profession", professionValid);
		userData.put("project", projectToEval);
		}catch(NullPointerException err){
			isDataOK = false;
			String dataError = getResources().getString(R.string.incompleteData);
			Toast.makeText(LogActivity.this, dataError, Toast.LENGTH_SHORT).show();
			
		}
		
		
		if (isDataOK){
			//segmentation userSegmentation = new mySegmentation(userData);
			//Toast.makeText(LogActivity.this, "ok", Toast.LENGTH_SHORT).show();
			
			setAssets(view);
			Intent intent = new Intent(this, ArActivity.class);
			intent.putExtra("USER_DATA_MESSAGE", userData);
			
			
		}
		
		
		
	}
	*/
	
	
	
	
	// from http://examples.javacodegeeks.com/android/core/ui/progressdialog/android-progressdialog-example/
	public void setAssets(View view) {
		boolean isDataOK = true;
		
		projectToEval  = selectedProject.getItemAtPosition(selectedProject.getSelectedItemPosition()).toString();
		
		/*
		 * Descomentar/comentar esto para revisar datos completos antes de lanzar actividad !!!!!!!!!!!!!!!!!!
		 * 
		 *
		 * */
		if ( emailValid == null || ageValid == null || socialStrataValid == null || genderValid == null 
				||professionValid == null || projectToEval == null){
			isDataOK = false;
			String dataError = getResources().getString(R.string.incompleteData);
			Toast.makeText(LogActivity.this, dataError, Toast.LENGTH_SHORT).show();
		}
		
		
		
		
		if (isDataOK && isServer){
			Context ctx = getApplicationContext();
			boolean isAlready = myProjectManager.deployAssets(projectToEval, ctx );
			
			
			if (!isAlready){
				barProgressDialog = new ProgressDialog(LogActivity.this);
				barProgressDialog.setTitle("Preparando proyecto ...");
				barProgressDialog.setMessage("Download in progress ...");
				barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
				barProgressDialog.setIndeterminate(false);
				barProgressDialog.setProgress(0);
				barProgressDialog.setMax(100);
				barProgressDialog.show();
				
				AssetsProgress = 0;
				
				
				
			
			
				new Thread(new Runnable() {

				public void run() {

						
						// Here you should write your time consuming task...
						while (AssetsProgress < 100) {
							AssetsProgress = myProjectManager.getDeployPercentage();
							
	
							updateBarHandler.post(new Runnable() {
	
	                            public void run() {
	                            	//int progress = myProjectManager.getDeployPercentage();
	                            	barProgressDialog.setProgress(AssetsProgress);
	                            	//Log.i(tag, String.valueOf(progress));

	
	                              }
	
	                          });
							
							// ok, file is downloaded,
							if (AssetsProgress >= 100) {
			 
								// sleep 2 seconds, so that you can see the 100%
								try {
									Thread.sleep(5000);
	
									Intent intent = new Intent(getApplicationContext(), ArActivity.class);
									intent.putExtra("USER_DATA_MESSAGE", userData);
									startActivity(intent);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								// close the progress bar dialog
								barProgressDialog.dismiss();
							}
						}

				}
			}).start();
			}else{
				Toast.makeText(LogActivity.this, "Proyecto ya existe", Toast.LENGTH_SHORT).show();
				//Toast.makeText(LogActivity.this, projectToEval, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), ArActivity.class);
				/*
				intent.putExtra("email", emailValid);
				intent.putExtra("age", ageValid);
				intent.putExtra("social", socialStrataValid);
				intent.putExtra("gender", genderValid);
				intent.putExtra("profession", professionValid);
				intent.putExtra("USER_DATA_MESSAGE", userData);
				*/
				
				//String[] userData = {projectToEval, emailValid, ageValid, socialStrataValid, genderValid, professionValid};
				
				
				intent.putExtra(PROJECT_EXTRA, projectToEval );
				intent.putExtra(EMAIL_EXTRA , emailValid);
				intent.putExtra(AGE_EXTRA , ageValid);
				intent.putExtra(SOCIAL_EXTRA , socialStrataValid);
				intent.putExtra(GENDER_EXTRA , genderValid);
				intent.putExtra(PROFESSION_EXTRA , professionValid);
				
				
				startActivity(intent);
			}
			
			
		
		}else{
			Toast.makeText(LogActivity.this, "Iniciando proyecto por defecto", Toast.LENGTH_SHORT).show();
			//Toast.makeText(LogActivity.this, projectToEval, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getApplicationContext(), ArActivity.class);
			
			Bundle extras = new Bundle();
			
			
			extras.putString(PROJECT_EXTRA, projectToEval );
			/*
			extras.putString(EMAIL_EXTRA , emailValid);
			extras.putString(AGE_EXTRA , ageValid);
			extras.putString(SOCIAL_EXTRA , socialStrataValid);
			extras.putString(GENDER_EXTRA , genderValid);
			extras.putString(PROFESSION_EXTRA , professionValid);
			*/
			
			intent.putExtras(extras);
			
			startActivity(intent);
		}
		
		
	}
		
	private void alert(Context context){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
 
			// set title
			alertDialogBuilder.setTitle("Sin conexión");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("No se ha podido establecer una conexión con el servidor, ¿desea intentar?")
				.setCancelable(false)
				.setPositiveButton("Si",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						//se es "si", volver a intentar obtener la lista de proyectos
						listProjects = myProjectManager.getProjectsAvailable();//List of available projects in server
						
						if(listProjects.length == 1 && listProjects[0] == "default" ){//si no hay conexion con el servidor
						//	alert(getApplicationContext());
						}
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			}
	

}


