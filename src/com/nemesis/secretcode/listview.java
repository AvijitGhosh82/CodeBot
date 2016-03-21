package com.nemesis.secretcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

@SuppressWarnings("unused")
public class listview extends SherlockActivity implements OnItemClickListener {

    int flag_tag = 0;
    Context context;
    protected ActionMode mActionMode;
    public int Select = -1;
    protected int theme = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	loadTheme();
    	super.setTheme(theme);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        // Show the Up button in the action bar.
     	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView list = (ListView) findViewById(R.id.listView);

        final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode,
					Menu menu) {
				// TODO Auto-generated method stud
				
				return true;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				mActionMode = null;
				
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode,
					com.actionbarsherlock.view.Menu menu) {
				// TODO Auto-generated method stub
				getSupportMenuInflater().inflate(R.menu.context,menu);
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode,
					com.actionbarsherlock.view.MenuItem item) {
				AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
				
				// TODO Auto-generated method stub
				 switch (item.getItemId()) {
		            case R.id.edit:
		            	Log.d("TAG","This part ok");
		                editRow(Select);
		                mode.finish();
		                return true;
		            case R.id.delete:
		                deleteRow(Select);
		                mode.finish();
		                return true;
		            default:
		                return false;
				 }
			}
    };
    list.setOnItemLongClickListener(new OnItemLongClickListener()
    {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			if(mActionMode!=null)
				return false;
			Select = arg2;
			mActionMode = listview.this.startActionMode(mActionModeCallback);
			arg1.setSelected(true);
			return true;
		}
    	
    });
    LoadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_list, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	final Dialog dialog = new Dialog(listview.this);
    	if(item.getItemId()== R.id.menu_add)
    	{
    		dialog.setContentView(R.layout.popup);
    		dialog.setTitle("Add CodeBot Friend");
    		dialog.setCancelable(true);
    		Button button = (Button) dialog.findViewById(R.id.button1);
            button.setOnClickListener(new OnClickListener()
            {

    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    		        final EditText ET1 =(EditText) dialog.findViewById(R.id.editText1);
    		        final EditText ET2 =(EditText) dialog.findViewById(R.id.editText2);   	
    				String name = ET1.getText().toString();
    				String code = ET2.getText().toString();
    				flag_tag++;
    				SaveTag();
    				SavePreferences("name"+flag_tag,name);
    				SavePreferences("code"+flag_tag,code);
    				LoadData();
    				dialog.cancel();
    			}
            
            	 
            });
    		dialog.show();
    	}
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
		return true;
     
    }
    private void SavePreferences(String key, String value){
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(listview.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
       }

    private void SaveTag(){
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(listview.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("TAG",flag_tag);
        editor.commit();
       }
    private void LoadData()
    {   
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(listview.this);
    	int tag = sharedPreferences.getInt("TAG", 0);
        
    	final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    	Log.d("TAG",Integer.toString(tag));
	   	SimpleAdapter adapt = new SimpleAdapter(listview.this,list,
	    R.layout.list_item,
		new String[] { "name","code" },
		new int[] {R.id.listItem1, 
		R.id.listItem2});
    	for(int i=1;i<=tag;i++)
		{
    		HashMap<String,String> item = new HashMap<String,String>();
    		String name = sharedPreferences.getString("name"+i, null);
		    String code = sharedPreferences.getString("code"+i, null);
		    item.put("name", name);
		    item.put("code", code);
		    Log.d("TAG", name+" "+code);
		    list.add(item);
		    adapt.notifyDataSetChanged();
		    
		}
    	ListView views = (ListView) findViewById(R.id.listView);
    	views.setAdapter(adapt);
		 flag_tag = tag;
    }

    public void deleteRow(int id)
    {
    	int flagid = id+1;
    	for(int i= flagid;i<flag_tag;i++)
    	{
    		int temp = i+1;
    		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(listview.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name"+i, sharedPreferences.getString("name"+temp, null));
            editor.putString("code"+i, sharedPreferences.getString("code"+temp, null));
            editor.commit();
    	}
    	flag_tag--;
    	SaveTag();
    	LoadData();
    }
    public void editRow(final int id)
    {
    	final Dialog dialog = new Dialog(listview.this);
    	dialog.setContentView(R.layout.popup);
    	dialog.setTitle("Edit CodeBot Friend");
    	dialog.setCancelable(true);
    	Button btn = (Button) dialog.findViewById(R.id.button1);
    	btn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText ET1 =(EditText) dialog.findViewById(R.id.editText1);
		        final EditText ET2 =(EditText) dialog.findViewById(R.id.editText2); 
		        int temp = id + 1;
		        SavePreferences("name"+temp,ET1.getText().toString());
				SavePreferences("code"+temp,ET2.getText().toString());
				LoadData();
				dialog.cancel();
			}
    		
    	});
    	dialog.show();
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	protected void loadTheme()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(listview.this);
		theme = sharedPreferences.getInt("Theme",R.style.AppTheme);
		this.getApplicationContext().setTheme(theme);		
	}
    

}
