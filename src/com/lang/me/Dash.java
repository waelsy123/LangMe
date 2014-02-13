// *************************************//
// Copyright: Wael Almattar				//
//	Email   : waelsy123@gmail.com       //
//	Tel     : +48537884038				//
// Github   : github.com/waelsy123      //
//**************************************//

package com.lang.me;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Dash extends Activity implements OnItemSelectedListener {

	String username = null ; 
	String lang = "English" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash);
		
		
		Intent intent = getIntent();
		username = intent.getStringExtra(ParseStarterProjectActivity.EXTRA_MESSAGE);
		//fill spinner 
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
				});
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dash, menu);
		return true;
	}

	public void goToNewPost( View view ){
		Intent intent = new Intent(this, NewPost.class);
	    intent.putExtra( ParseStarterProjectActivity.EXTRA_MESSAGE , username );

	    startActivity(intent);
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view , int pos ,
			long id ) {
		// TODO Auto-generated method stub
		lang= parent.getItemAtPosition(pos).toString() ;
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void browserMe(View view){
		
		Intent intent = new Intent(this, BrowserByLang.class);
	    intent.putExtra( ParseStarterProjectActivity.EXTRA_MESSAGE , username );
	    String t = "me" ;
	    intent.putExtra( "lang" , t);
	    
	    startActivity(intent);
	}
	
	public void signOut(View view) {
		Intent intent = new Intent(this, Pram.class);
		startActivity(intent);
	}
	
	public void goToBrowserByLang(View view ) { 
		Intent intent = new Intent(this, BrowserByLang.class);
	    intent.putExtra( ParseStarterProjectActivity.EXTRA_MESSAGE , username );
	    intent.putExtra( "lang" , lang );
	    
	    startActivity(intent);
	}
	
}
