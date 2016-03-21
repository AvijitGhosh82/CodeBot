package com.nemesis.secretcode;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import java.util.StringTokenizer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

@SuppressLint("NewApi")
public class EncodeActivity extends SherlockActivity {

	Button mButton;
	EditText mEdit, mKey;
	protected int theme = 0;
	String fin = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		loadTheme();
		super.setTheme(theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encode);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mButton = (Button)findViewById(R.id.encodeBut);
		mEdit = (EditText)findViewById(R.id.encodeinput);
		mKey = (EditText)findViewById(R.id.encodekey);
		mButton.setOnClickListener(
				new OnClickListener()
				{
				public void onClick(View view)
				{
					String s1 = mEdit.getText().toString();
					int k= Integer.valueOf(mKey.getText().toString());
					String temp;int i;fin="";
			        StringTokenizer st=new StringTokenizer(s1);
			        while(st.hasMoreTokens())
			        {
			            temp=st.nextToken();
			            StringBuffer sb=new StringBuffer(temp);
			            for(i=0;i<temp.length();i++)
			            {
			                sb.setCharAt(i,(char)((int)temp.charAt(i)+k));
			            }
			            sb.reverse();
			            fin+=sb.toString()+" ";
			            k++;
			        }
			        EditText text = (EditText) findViewById(R.id.encodeoutput);
			        text.setText(fin);
					
				}
				});
		
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_encode, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
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
		case R.id.menu_refresh:
			Intent i = new Intent(EncodeActivity.this,EncodeActivity.class);
			startActivity(i);
			finish();
            return true;
		case R.id.menu_copy:
			EditText mout = (EditText)findViewById(R.id.encodeoutput);
			String extract = mout.getText().toString();
			if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
				android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			    clipboard.setText(extract);
			} else {
			    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			    android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", extract);
			            clipboard.setPrimaryClip(clip);
			}
			Toast.makeText(getApplicationContext(), "Output copied to clipboard", Toast.LENGTH_SHORT).show();
            return true;    
            
		}
		return super.onOptionsItemSelected(item);
	}
	protected void loadTheme()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EncodeActivity.this);
		theme = sharedPreferences.getInt("Theme",R.style.AppTheme);
		this.getApplicationContext().setTheme(theme);		
	}
	
	public void encodeSend(View view) {
	    // starts encode activity
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, fin);
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
	}

}
