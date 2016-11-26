package com.yard.stick.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.imaginem.Imaginem;
import org.imaginem.exceptions.ImaginemExcpetion;
import org.imaginem.items.ImageItem;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class Voting  {

	private final String DB_NAME = "fakeClientsDb";
	private final String TABLE_NAME = "clients";
	private Activity m_Activity;
	private SQLiteDatabase m_DB;
	
	
	public Voting(Activity activity) {
		super();
		m_Activity = activity; 
		    try {
	        	m_DB =  m_Activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
	        	
	        	m_DB.execSQL("CREATE TABLE IF NOT EXISTS " +
	        			TABLE_NAME +
	        			" (id INTEGER PRIMARY KEY, ClientID VARCHAR);");
	        	
	        } catch (SQLiteException se ) {
	        	//Log.e(getClass().getSimpleName(), "Could not create or Open the database");
	        } finally {
	        	if (m_DB != null) 
	        		m_DB.close();
	        }  
	        
	}
	
/*	
	private String getNewID() {

		String id = UUID.randomUUID().toString();
		m_DB.execSQL("INSERT INTO " +
    			TABLE_NAME +
    			" Values (null,'"+id+"');");
		
		return id;
	}
	
	
	private int getIDCount() {
		int rtn = 0;
		
		Cursor c = m_DB.rawQuery("SELECT ClientID FROM " + TABLE_NAME, null);

		if (c != null) {
			rtn = c.getCount();
		}
		return rtn;
		
	}
		private List<String> getClientIDs(int count)
	{
		ArrayList<String> results = new ArrayList<String>();

		Cursor c = m_DB.rawQuery("SELECT ClientID FROM " + TABLE_NAME + " LIMIT " + Integer.toString(count), null);

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					String clientID = c.getString(c.getColumnIndex("ClientID"));
					results.add(clientID);
				} while (c.moveToNext());
			}
		}
		
		return results;
	}
	
*/
	public void VoteUp(Imaginem v2wrapper, ImageItem item, int count) {
		m_DB =  m_Activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		List<String> clientIDs = new ArrayList<String>();
		
		while(clientIDs.size() < count)
		{
			String newID = UUID.randomUUID().toString();
			clientIDs.add(newID);
			try {
				v2wrapper.getUser().firstContact(newID);
			} catch (ImaginemExcpetion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int iLoop = 0; iLoop<count; iLoop++)
		{
			
			try {
				v2wrapper.getImage().voteUp(clientIDs.get(iLoop),item.getImageID());
			} catch (ImaginemExcpetion e) {
				e.printStackTrace();
			}
		}
		
		if (m_DB != null) 
    		m_DB.close();
	}
	
	public void VoteDown(Imaginem v2wrapper, ImageItem item, int count) {
		m_DB =  m_Activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		
		List<String> clientIDs = new ArrayList<String>();
		
		while(clientIDs.size() < count)
		{
			String newID = UUID.randomUUID().toString();
			clientIDs.add(newID);
			try {
				v2wrapper.getUser().firstContact(newID);
			} catch (ImaginemExcpetion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int iLoop = 0; iLoop<count; iLoop++)
		{
			
			try {
				v2wrapper.getImage().voteDown(clientIDs.get(iLoop),item.getImageID());
			} catch (ImaginemExcpetion e) {
				e.printStackTrace();
			}
		}
		
		if (m_DB != null) 
    		m_DB.close();
	}
	
}
