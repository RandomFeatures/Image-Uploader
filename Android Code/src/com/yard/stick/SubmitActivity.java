package com.yard.stick;

import org.imaginem.Imaginem;
import org.imaginem.exceptions.ImaginemExcpetion;

import com.yard.stick.util.GoogleAccount;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SubmitActivity extends ListActivity  {

	private Imaginem m_v2Wrap;
	
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String InstaID = ((InstagramAdapter) getListAdapter()).getInstagramID(position);
		String msg = "The Image has been submitted!";
		
	    try {
			if(!m_v2Wrap.getImage().submitInstagram(InstaID))
			{
				msg = "There was a problem submitting";
			}
		} catch (ImaginemExcpetion e) {
			e.printStackTrace();
		}

	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage(msg).create().show();

		
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_v2Wrap = new Imaginem(GoogleAccount.getUsername(this));
        
        setContentView(R.layout.submit);
        InstagramAdapter insta = new InstagramAdapter(this);
        insta.loadInstagramList();
        
        setListAdapter(insta);
    }
    
    

}
