// *************************************//
// Copyright: Wael Almattar				//
//	Email   : waelsy123@gmail.com       //
//	Tel     : +48537884038				//
// Github   : github.com/waelsy123      //
//**************************************//

package com.lang.me;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class Post extends Activity {
	 int num = 0; 
	 TextView tv = null ; 
	ArrayList<String> l = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	String username = null; 
	String postId = null ; 
	String lang = null ; 
	String post = null ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		
		Intent intent = getIntent();
		
		username = intent.getStringExtra(ParseStarterProjectActivity.EXTRA_MESSAGE);
		lang = intent.getStringExtra("lang") ; 
		postId = intent.getStringExtra("postId") ; 
		post = intent.getStringExtra("post") ;
		
		tv = (TextView) findViewById(R.id.textView1 );
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Posts");
	    query1.whereEqualTo("objectId", postId );
	    query1.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> objects, ParseException e) {
				if(e!=null)
				Log.d( "Error:" , e.toString()) ;
				else{
					for (ParseObject item : objects) {
						tv.setText(item.getString("post")) ; 

						break ;
				            
				        }
				    }
			}
				
		} );

		
	
	

		
		 
		ListView lv = (ListView) findViewById(R.id.listView1); 
	      adapter = new ArrayAdapter<String>(this, R.layout.da_comment  );
	        
	      ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
	      query.whereEqualTo("postId", postId );
	      query.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> objects, ParseException e) {
					if(e!=null)
					Log.d( "Error:" , e.toString()) ;
					else{
						num = objects.size() ;
					    if (objects.size() == 0) {
					    	
					    	adapter.add( "There is no items..");
					    }
					    else {
					        for (ParseObject item : objects) {
					            adapter.add( item.getString("username").toString() + ": " + item.getString("comment").toString() );
					            lang = item.getString("lang" ) ; 	
					            
					        }
					    }
				
					}
				
				}
					
			} );


			
	        
	lv.setAdapter(adapter);
			
	}
	public  void set(int key, int num) {
		num = key  ;
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post, menu);
		return true;
	}
	
	public void addComment(View view ) {
		EditText ed = (EditText) findViewById(R.id.editText1) ;
		String comment = ed.getText().toString() ; 
		
		if(comment.length()<10){
			wael("Comment's length must be more than 10 letters");
		}
		else{	
			
			int readNum = 1 ; 
			
			ParseObject po = new ParseObject("Comment");
			po.put("type", "comments");
			po.put("username", username);
			po.put("postId" , postId );
			po.put("readNum" , readNum );
			po.put("lang" , lang ) ;
			po.put("comment", comment );
			
			po.saveInBackground();
			
			Toast.makeText( this , "your comment has been saved: " + comment , Toast.LENGTH_LONG  ).show();
			Refresh() ;
		}
		
	}
	public void Refresh(){
			Intent intent = new Intent(this, Post.class);
			    
			    
			    intent.putExtra(ParseStarterProjectActivity.EXTRA_MESSAGE, username );
			    intent.putExtra( "postId" , postId  );
			    intent.putExtra( "lang" , lang  );
			    intent.putExtra( "post" ,  post );
			 
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
