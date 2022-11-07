package co.edu.eafit.services.dataValidation;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class validationMethods {
	
	public static  String Is_Valid_Email(EditText edt, String error) {
		String emailValid = null;	
		if (edt.getText().toString() == null) {
			edt.setError("Invalid Email Address");
			emailValid = null;
		} else if (isEmailValid(edt.getText().toString()) == false) {
			edt.setError(error);
			emailValid = null;
		} else {
			emailValid = edt.getText().toString();
		}
		return emailValid;
		
		
	}

	private static boolean isEmailValid(CharSequence email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	} // end of email matcher
	
	
	
	
	
	public static String age_validation(int maxAge, EditText edt, String error) throws NumberFormatException {
		String ageValid = null;
		int MinLen, MaxLen;
		
		Calendar c = Calendar.getInstance(); 
		int currentYear = c.get(Calendar.YEAR);
		
		MinLen = currentYear - maxAge;
		MaxLen = currentYear - 3;
		
		
		if (edt.getText().toString().length() <= 0) {
			edt.setError(error);
			ageValid = null;
		} else if (Double.valueOf(edt.getText().toString()) < MinLen
				|| Double.valueOf(edt.getText().length()) > MaxLen) {
			edt.setError(error);
			ageValid = null;
		} else {
			ageValid = edt.getText().toString();
			// Toast.makeText(getApplicationContext(),
			// ""+edt.getText().toString(), Toast.LENGTH_LONG).show();
		}
		
		return ageValid;

	} // END OF Edittext validation
	
	
	
	public static String socialStrata_validation(Spinner spnr){
		String tag = "social_strata_validation";
		
		String socialStrataValid = null;
		
		if(spnr.getSelectedItem().toString().length() > 2){
			//Log.i(tag, spnr.getSelectedItem().toString());
			
			socialStrataValid = null;
		}else{
			socialStrataValid = spnr.getSelectedItem().toString();
		}
		
		return socialStrataValid;
	}
	
	public static String gender_validation(Spinner spnr){
		String tag = "gender_validation";
		
		String gender = null;
		String spinnerText = spnr.getSelectedItem().toString();
		Log.i(tag, spnr.getSelectedItem().toString());
		if(spinnerText.equals("Género")){
			Log.i(tag, spnr.getSelectedItem().toString());
			
			gender = null;
		}else{
			gender = spnr.getSelectedItem().toString();
		}
		
		return gender;
	}
	
	
	public static  String profession_validation(EditText edt, String error) {
		String profession = null;	
		if (edt.getText().toString() == null) {
			edt.setError(error);
			profession = null;
		} else {
			profession = edt.getText().toString();
		}
		return profession;
		
		
	}
	
	

}
