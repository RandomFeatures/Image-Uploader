/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yard.stick;
//NOT USING
import java.util.Collections;
import java.util.List;

import org.imaginem.Imaginem;
import org.imaginem.exceptions.ImaginemExcpetion;
import org.imaginem.items.ImageItem;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yard.stick.util.GoogleAccount;

public class ImageAdapter extends BaseAdapter {

	private Activity m_Activity;
	private Imaginem m_v2Wrap;
    private final ImageLoader imageLoader;
	private List<ImageItem> m_NewestList = Collections.emptyList();
	private static LayoutInflater inflater = null;
	private boolean m_Loaded = false;
	private boolean m_Loading = false;
	private int m_ImageListType = 0;
	
	public ImageAdapter(Activity activity, int listtype) {
		super();
		m_ImageListType = listtype;
		m_Activity = activity;
		m_v2Wrap = new Imaginem(GoogleAccount.getUsername(activity));
		imageLoader = new ImageLoader(activity);
		inflater = (LayoutInflater)m_Activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d("WTF", "create adapter");
	}

    
    
    public int getCount() {
        return m_NewestList.size();
    }

    public String getItem(int position) {
    	String rtn = "";
		if(m_Loaded)
			rtn = m_NewestList.get(position).getImageURL();
		return rtn;
    }

    public long getItemId(int position) {
    	Long rtn = 0l;
		if(m_Loaded)
			rtn = (long)m_NewestList.get(position).getImageURL().hashCode();
		return rtn;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        
    	View inflatedView = convertView;
		
		if(!m_Loading & !m_Loaded) loadImageList();
		
		if(position < m_NewestList.size())
		{
			try {
				
				if (convertView == null)
					inflatedView = inflater.inflate(R.layout.newestitem, null);
				
				ImageView image = (ImageView) inflatedView.findViewById(R.id.newimg);
				TextView txt = (TextView) inflatedView.findViewById(R.id.newimgtxt);
				StringBuilder s = new StringBuilder();
				double age = new Interval(new DateTime(m_NewestList.get(position).getDate()), new DateTime()).toPeriod().toStandardHours().getHours();
				
				s.append("Up Votes: ");s.append(m_NewestList.get(position).getUpVotes());s.append("\n");
				s.append("Down Votes: ");s.append(m_NewestList.get(position).getDownVotes());s.append("\n");
				s.append("Score: ");s.append(m_NewestList.get(position).getScore());s.append("\n");
				s.append("Age: ");s.append(age);s.append(" hours\n");
				txt.setText(s.toString()); 
				
				imageLoader.DisplayImage(m_NewestList.get(position).getImageURL(), image);
			} catch (Exception e) {
				System.out.println(m_NewestList.get(position).getCaption());
			}
		}
		return inflatedView;
    	
    }

    public void loadImageList()
	{
    	m_Loading = true;
    	m_NewestList.clear();
		try {
			
			switch(m_ImageListType)
			{
			case 0:
				m_NewestList = m_v2Wrap.getImage().getNewest();
				break;
			case 1:
				m_NewestList = m_v2Wrap.getImage().getTopRanked();
				break;
			}
			if(m_NewestList.size() > 0)
				m_Loaded = true;
		} catch (ImaginemExcpetion e) {
			e.printStackTrace();
		}finally {
			m_Loading = false;
		}
		
	}
    
    
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    
    public String getImageID(int position)
	{
		String rtn = "";
		if(m_Loaded)
			rtn = m_NewestList.get(position).getImageID();
		return rtn;
	}
	
	public String getImageURL(int position) {
		String rtn = "";
		if(m_Loaded)
			rtn = m_NewestList.get(position).getImageURL();
		return rtn;
	}
	
	public ImageItem getImageItem(int position){
		ImageItem rtn = null;
		if(m_Loaded)
			rtn = m_NewestList.get(position);
		return rtn;
	}
    
}
