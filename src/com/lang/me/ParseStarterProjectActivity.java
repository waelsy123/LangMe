package com.lang.me;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;



public class ParseStarterProjectActivity extends Activity {
	ProgressDialog progressDialog = null ; 
	
	  int  key = -1   ; 
	  public final static String EXTRA_MESSAGE = "com.example.starter.MESSAGE";
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ParseAnalytics.trackAppOpened(getIntent());
	}
	
	
	private void showProgressDialog() { 
	    progressDialog = new ProgressDialog(this);
	    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    progressDialog.setMessage("Please wait..");
	    progressDialog.show();
	}

	private void dismissProgressDialog() {
	    if(progressDialog != null)
	        progressDialog.dismiss();
	}
	
	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	
	public void clearuser(View view){
		
		EditText user = (EditText) findViewById(R.id.editText1);
		user.requestFocus(); 
		
		user.setText("") ;
	}
	
	
	public void SignUp(View view){ 
		
		showProgressDialog(); 
		
		 Parse.initialize(this, "BHZ96s2YvmANgiw5hXXYeqrVX4nf9RYiG9j71lE1", "jXIblFviyGREPASI3bu6jZNcT92n1KCDzUv7kSiz");

		EditText user  = (EditText) findViewById(R.id.editText1);
		EditText pass1 = (EditText) findViewById(R.id.editText2);
		EditText pass2 = (EditText) findViewById(R.id.editText3);
		EditText Email = (EditText) findViewById(R.id.editText4);
		
		final String username = user.getText().toString() ;
		final String password = pass1.getText().toString() ;
		final String confirm = pass2.getText().toString() ;
		final String email = Email.getText().toString() ;
		
		
		

		/*
		try {
			List<ParseObject> query1 = new ParseQuery<ParseObject>("SignUp").whereEqualTo("username" , username ).find() ;
		} catch (ParseException e) {
			wael(e.toString()) ;
		}
		*/
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SignUp");
		query.whereEqualTo("username", username );
		
		query.findInBackground(new FindCallback<ParseObject>() {
		 
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				dismissProgressDialog(); 
				if(objects.size() == 0 ){
					if(!isEmailValid(email)){
	        			wael("Please enter a valid format Email!"  );	
	        		}
	        		else if(!password.equals(confirm)){
	        				wael("Please write same password 2 times!"  );
	        			} // confirm password.
	        		else if(username.length() < 4 )
	        			wael("Username's length must be more or equal to 4");
	        		else if(password.length() < 6)
	        			wael("Password's length must be more or equal to 6");
	        		else{
	        			ParseObject sign = new ParseObject("SignUp");
	        			sign.put("type", "users");
	        			sign.put("username", username);
	        			sign.put("password", password);
	        			sign.put("email" , email);
	        			
	        			sign.saveInBackground();
	        			
	        			Toast.makeText( ParseStarterProjectActivity.this , "Your account '" +  username +  "' has been registered!" , Toast.LENGTH_LONG  ).show();
	        			dismissProgressDialog(); 
	        			goToSignIn( "this is the message!");
	        		    
	        		    
	        				
	        		}
	        		
					
					
					
				}
				else {
					
					wael("Please choose another username."  + objects.size() ) ;
					
					//Object[] s = objects.toArray() ;
					//	for (ParseObject item : objects) {
					//	wael(item.getString("password"));
					}
				
				
			}
		});
		
		
		
	}
	
	
	
	

	public void goToSignIn(String s ){
		Intent intent = new Intent(this, Pram.class);
	    
		dismissProgressDialog(); 
	    intent.putExtra(EXTRA_MESSAGE, s );
	    startActivity(intent);
	}
	
	public void wael(String s){
		
		
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

		dlgAlert.setMessage( s  );
		dlgAlert.setTitle("LangMe");
		dlgAlert.setPositiveButton("Ok",
			    new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			          //dismiss the dialog  
			        }
			    });
		
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
		
	}
}


