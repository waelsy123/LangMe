// *************************************//
// Copyright: Wael Almattar				//
//	Email   : waelsy123@gmail.com       //
//	Tel     : +48537884038				//
// Github   : github.com/waelsy123      //
//**************************************//


package com.lang.me;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


@SuppressLint("ShowToast")
public class BrowserByLang extends Activity {
	
	ProgressDialog progressDialog = null ; 
	
	int N = 1 ;
	ListView lv = null ;
	Button btnLoadMore = null;
	protected static final int Toast_LENGTH_LONG = 0;
	String lang = null; 
	String username = null; 
	ArrayList<String> l = new ArrayList<String>();
	
	   ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser_by_lang);
		
		showProgressDialog() ; 
		
		Intent intent = getIntent();
		username = intent.getStringExtra(ParseStarterProjectActivity.EXTRA_MESSAGE);
		lang = intent.getStringExtra("lang") ; 
		

		adapter = new ArrayAdapter<String>(this, R.layout.da_item  );
		// Creating a button - Load More
		lv = (ListView) findViewById(R.id.listView1); 
		btnLoadMore = new Button(this) ;
		btnLoadMore.setText("Load More");
		btnLoadMore.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View view) {
				
				
				 ParseQuery<ParseObject> query = ParseQuery.getQuery("Posts");
			        if(! lang.equals("me")){
			        	query.whereEqualTo("lang", lang );
			        }
			        else {
			        	query.whereEqualTo("username", username );
			      
			        }
			        query.setSkip(20*N);
			        N++; 
				    query.setLimit(20);
					query.findInBackground(new FindCallback<ParseObject>() {
						public void done(List<ParseObject> objects, ParseException e) {
							if(e!=null)
							Log.d( "Error:" , e.toString()) ;
							else{
							    if (objects.size() == 0) {
							    	//wael("NIC!") ;
							    } else {
							        for (ParseObject item : objects) {
							        	String text = item.getString("post").toString() ;
							        	if(text.length() > 90 ){
							        		text = text.substring(0,90).toString() + "...\nAuther: " + item.getString("username") ;
							        	}
							        	else {
							        		text = text + "...\nAuther: " + item.getString("username") ;
							        	}
							            adapter.add( text );
							            lang = item.getString("lang" ) ; 
							            
							            try{
							            	l.add(item.getObjectId().toString()) ;
							            }
							            catch(Exception ddd){
							            	Log.d("error" , ddd.toString() );
							            }
							        }
							   
							    	
							    	
							  }
						
							}
							
						}
							
					} );
				lv.setAdapter(adapter);
			}
			
		});
	
		
		
	
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Posts");
        if(! lang.equals("me")){
        	query.whereEqualTo("lang", lang );
        }
        else {
        	query.whereEqualTo("username", username );
      
        }
	    query.setLimit(20);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> objects, ParseException e) {
				if(e!=null)
				Log.d( "Error:" , e.toString()) ;
				else{
				    if (objects.size() == 0) {
				    	wael("NIC!") ;
				    } else {
				        for (ParseObject item : objects) {
				        	String text = item.getString("post").toString() ;
				        	if(text.length() > 90 ){
				        		text = text.substring(0,90).toString() + "...\nAuther: " + item.getString("username") ;
				        	}
				        	else {
				        		text = text + "...\nAuther: " + item.getString("username") ;
				        	}
				            adapter.add( text );
				            lang = item.getString("lang" ) ; 
				            
				            try{
				            	l.add(item.getObjectId().toString()) ;
				            }
				            catch(Exception ddd){
				            	Log.d("error" , ddd.toString() );
				            }
				        }
				   
				    	
				    	
				  }
			
				}
				lv.addFooterView(btnLoadMore);
			
			}
				
		} );

		lv.setAdapter(adapter);
       
		dismissProgressDialog(); 
		
       lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

   		@Override
   		public void onItemSelected(AdapterView<?> parent , View viewClicked , int pos,
   				long id) {
   			TextView tv =  (TextView)viewClicked ;
   			Intent intent = new Intent(BrowserByLang.this, Post.class);
   		    
   		    
   		    intent.putExtra(ParseStarterProjectActivity.EXTRA_MESSAGE, username );
   		    intent.putExtra( "postId" , l.get(pos).toString()  );
   		    intent.putExtra( "lang" , lang  );
   		    intent.putExtra( "post" ,  tv.getText().toString() );
   		 
   		    startActivity(intent);

   		}

   		@Override
   		public void onNothingSelected(AdapterView<?> arg0) {
   			// TODO Auto-generated method stub
   			
   		}
   	}) ;
          
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browser_by_lang, menu);
		return true;
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
