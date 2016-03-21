package com.nemesis.secretcode;

import java.io.IOException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nemesis.secretcode.listview;

@SuppressWarnings("unused")
public class MainActivity extends SherlockActivity {
    
	int theme = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		loadTheme();	
		super.setTheme(theme);
		/*
		 * 
		 * 
		 */
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
					   
	}
	@Override
	protected void onRestart()
	{
		super.onRestart();
		Intent intent = new Intent(MainActivity.this,MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_help:
	        	Intent intent = new Intent(this, HelpActivity.class);
	    	    startActivity(intent);
	            return true;
	        case R.id.menu_list:
	        	Intent intent2 = new Intent(this, listview.class);
	    	    startActivity(intent2);
	            return true;
	        case R.id.menu_more:
	        	Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://play.google.com/store/apps/developer?id=NEMESIS%E2%84%A2"));
                startActivity(myWebLink);
	            return true;
	        case R.id.menu_lightTheme:
	        	saveTheme(R.style.LightTheme);
	        	onRestart();
	            return true;
	        case R.id.menu_darkTheme:
	        	saveTheme(R.style.DarkTheme);
	        	onRestart();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void encodestarter(View view) {
	    // starts encode activity
		Intent intent = new Intent(this, EncodeActivity.class);
	    startActivity(intent);
	}
	
	public void decodestarter(View view) {
	    // starts encode activity
		Intent intent = new Intent(this, DecodeActivity.class);
	    startActivity(intent);
	}
	protected void saveTheme(int str)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Theme", str);
        editor.commit();
	}
	protected void loadTheme()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		theme = sharedPreferences.getInt("Theme",R.style.AppTheme);
		String var = (theme==2131427419)?"Light":"Dark";
		Log.d("Tag",var);
		super.setTheme(theme);
		
		
	}
	
}
