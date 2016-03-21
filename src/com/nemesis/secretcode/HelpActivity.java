package com.nemesis.secretcode;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.widget.RelativeLayout;

public class HelpActivity extends SherlockActivity {

	protected int theme = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		loadTheme();
		super.setTheme(theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_help, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void loadTheme()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HelpActivity.this);
		theme = sharedPreferences.getInt("Theme",R.style.AppTheme);
		String var = (theme==2131427419)?"Light":"Dark";
		Log.d("Tag",var);
		this.getApplicationContext().setTheme(theme);
		
		
	}
}
