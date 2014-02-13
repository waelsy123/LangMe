// *************************************//
// Copyright: Wael Almattar				//
//	Email   : waelsy123@gmail.com       //
//	Tel     : +48537884038				//
// Github   : github.com/waelsy123      //
//**************************************//

package com.lang.me;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class Pram extends Activity {
	public ProgressDialog progressDialog = null ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_pram);
		    
		    Parse.initialize(this, "BHZ96s2YvmANgiw5hXXYeqrVX4nf9RYiG9j71lE1", "jXIblFviyGREPASI3bu6jZNcT92n1KCDzUv7kSiz");

		    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pram, menu);
		return true;
	}

	public void signin(View view){
		
		showProgressDialog() ; 
		
		EditText user = (EditText) findViewById(R.id.editText1); 
		EditText pass = (EditText) findViewById(R.id.editText2); 
		
		final String username = user.getText().toString(); 
		final String password = pass.getText().toString() ; 
		
			ParseQuery<ParseObject> query = ParseQuery.getQuery("SignUp");
			query.whereEqualTo("username", username );
			query.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> objects, ParseException e) {
					dismissProgressDialog() ;
					
					if(e!=null)
					Log.d( "Error:" , e.toString()) ;
					else{
					    if (objects.size() == 0) {
					    	wael("The username is wrong!") ;
					    } else {	
					    	for (ParseObject item : objects) { 
						    	if(! password.equals(item.getString("password").toString()) ) {						    
						    		wael("The password is wrong!") ; 
						    	
						    	break;
						    	}	
						    	else{
						    		// siemano!
							    	goToDash(username) ;
							    	break;
						    	}
					    	}
					   
					    	
					    	
					    	
					  }
					}
				}
					} );
			
			
			
			
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

	public void goToSignUp(View view ){
		Intent intent = new Intent(this, ParseStarterProjectActivity.class);
	
	    startActivity(intent);
	}
	public void goToDash(String s  ){
		dismissProgressDialog() ;
		Intent intent = new Intent(this, Dash.class);
	    intent.putExtra(ParseStarterProjectActivity.EXTRA_MESSAGE , s );
	    startActivity(intent);
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

}
