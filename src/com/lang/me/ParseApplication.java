// *************************************//
// Copyright: Wael Almattar				//
//	Email   : waelsy123@gmail.com       //
//	Tel     : +48537884038				//
// Github   : github.com/waelsy123      //
//**************************************//


package com.lang.me;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.PushService;



public class ParseApplication extends Application {



	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		  Parse.initialize(this, "BHZ96s2YvmANgiw5hXXYeqrVX4nf9RYiG9j71lE1", "jXIblFviyGREPASI3bu6jZNcT92n1KCDzUv7kSiz");


		
		  PushService.setDefaultPushCallback(this ,  Pram.class );
		  
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    

		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
	}
	


}
