// *************************************//
// Copyright: Wael Almattar				//
//	Email   : waelsy123@gmail.com       //
//	Tel     : +48537884038				//
// Github   : github.com/waelsy123      //
//**************************************//

package com.lang.me;

import com.parse.ParseObject;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewPost extends Activity implements OnItemSelectedListener {
	ProgressDialog progressDialog = null ;
	String username = null ; 
	String lang = null  ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_post);
		Intent intent = getIntent();
		username = intent.getStringExtra(ParseStarterProjectActivity.EXTRA_MESSAGE);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.langs, android.R.layout.simple_spinner_item);
		

		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		/// end
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view , int pos ,
					long id ) {

				lang= parent.getItemAtPosition(pos).toString() ;
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		}) ;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_post, menu);
		return true;
	}

	public void publish(View view){
		
		showProgressDialog();
	
		EditText Post = (EditText) findViewById(R.id.editText1);
			String post = Post.getText().toString() ;
			if(post.length()>30 ){
				int readNum = 1 ;
				int commentsNum = 0 ;
				
				ParseObject po = new ParseObject("Posts");
				po.put("type", "posts");
				po.put("username", username);
				po.put("post" , post );
				po.put("readNum" , readNum );
				po.put("commentsNum" , commentsNum );
				po.put("lang" , lang ) ;
				
				po.saveInBackground();
				
				dismissProgressDialog();
				
				Toast.makeText(this , "Your Post has been published!", Toast.LENGTH_LONG).show();
				goToDash(username) ; 
			}
			else {
				dismissProgressDialog();
				wael("Your Post's length must be more or equal to 30 letters!");
			}
		
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view , int pos ,
			long id ) {

		lang= parent.getItemAtPosition(pos).toString() ;
		wael(lang + pos);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void goToDash(String s  ){
		Intent intent = new Intent(this, Dash.class);
	    intent.putExtra( ParseStarterProjectActivity.EXTRA_MESSAGE , s );
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
