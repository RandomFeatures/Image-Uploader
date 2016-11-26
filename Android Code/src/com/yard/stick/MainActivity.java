package com.yard.stick;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	@Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        
        if(!isNetworkConnected())
        {
        	Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        	dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(dialogIntent);
        	Toast.makeText(this, "Yard Stick does not support an offline mode. Please connect to the internet.", Toast.LENGTH_LONG)
            .show();

            WaitForInternet waiting = new WaitForInternet();
            waiting.execute();

        }else
        	loadUI();
        
        
    }

	public boolean isNetworkConnected(){
		ConnectivityManager conMan = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = conMan.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnected();
	}
	
	public void loadUI(){
		
		final int starttab = 0; 
		final Resources res = getResources();
	        final TabHost tabHost = getTabHost();
	        
	        final Intent submitIntent = new Intent().setClass(this, SubmitActivity.class);
	        
	        
	        final TabSpec oneSpec = tabHost.newTabSpec("Submit Image")
	                					   .setIndicator("Submit Image", res.getDrawable(R.drawable.ic_tab_feedback))
	                					   .setContent(submitIntent);
	        tabHost.addTab(oneSpec);
	       
	        final Intent newIntent = new Intent().setClass(this, NewestActivity.class);
	        
	        final TabSpec twoSpec = tabHost.newTabSpec("Newest Images")
	        							   .setIndicator("Newest Images", res.getDrawable(R.drawable.ic_tab_link))
	        							   .setContent(newIntent);
	        
	        tabHost.addTab(twoSpec);
	       
	        final Intent topIntent = new Intent().setClass(this, TopRankedActivity.class);
	        
	        final TabSpec threeSpec = tabHost.newTabSpec("Top Ranked")
	           								 .setIndicator("Top Ranked", res.getDrawable(R.drawable.ic_tab_about))
	           								 .setContent(topIntent);
	        tabHost.addTab(threeSpec);
	       
	        
	        tabHost.setCurrentTab(starttab);
	}
	
	
	private class WaitForInternet extends AsyncTask<Void, Void, Void> {

	    @Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			loadUI();
		}

	    @Override
	    protected Void doInBackground(Void... params) {

			while(!isNetworkConnected()) 
			{ 
	        	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        } 
	        return null;
	    }

	}

	
}
