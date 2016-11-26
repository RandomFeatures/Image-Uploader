package com.yard.stick;

import java.util.Collections;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstagramAdapter extends BaseAdapter {
	
	private Activity m_Activity;
	private final Instagram m_Instagram = new Instagram("6804288d444949eea536868981f72e4a");
	private final ImageDownloader imageDownloader = new ImageDownloader();
	private List<MediaFeedData> m_PopList = Collections.emptyList();
	private static LayoutInflater inflater=null;
	private boolean m_Loaded = false;
	private boolean m_Loading = false;
	
	public InstagramAdapter(Activity activity) {
		super();
		m_Activity = activity;
		inflater = (LayoutInflater)m_Activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}

	
	@Override
	public int getCount() {
		return m_PopList.size();
	}

	@Override
	public Object getItem(int position) {
		Object rtn = null;
		if(m_Loaded)
			rtn = m_PopList.get(position).getImages().getStandardResolution().getImageUrl();
		return rtn;
	}

	@Override
	public long getItemId(int position) {
		Long rtn = 0l;
		if(m_Loaded)
			rtn = (long)m_PopList.get(position).getImages().getStandardResolution().getImageUrl().hashCode();
		return rtn;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View inflatedView = convertView;
		
		if(!m_Loading & !m_Loaded) loadInstagramList();
		
		if(position < m_PopList.size())
		{
			try {
				if (convertView == null)
					inflatedView = inflater.inflate(R.layout.submititem, null);

				TextView text = (TextView) inflatedView.findViewById(R.id.subtxt);
				text.setText(m_PopList.get(position).getCaption().getText());

				ImageView image = (ImageView) inflatedView.findViewById(R.id.subimg);

				imageDownloader.download(m_PopList.get(position).getImages().getThumbnail().getImageUrl(), image);
			} catch (Exception e) {
				System.out.println(m_PopList.get(position).getCaption());
			}
		}
		return inflatedView;
	        
	}
	
	public ImageDownloader getImageDownloader() {
		return imageDownloader;
	}

	public void loadInstagramList()
	{
		m_Loading = true;
		try {
			MediaFeed mediafeed =  m_Instagram.getPopularMedia();
			if(mediafeed != null )
			{
				m_PopList = mediafeed.getData();
				m_Loaded = true;
			}
		} catch (InstagramException e) {
			e.printStackTrace();
		}finally {
			m_Loading = false;
		}
	}
	
	public String getInstagramID(int position)
	{
		String rtn = "";
		if(m_Loaded)
			rtn = m_PopList.get(position).getId();
		return rtn;
	}
	
	public String getImageURL(int position) {
		String rtn = "";
		if(m_Loaded)
			rtn = m_PopList.get(position).getImages().getStandardResolution().getImageUrl();
		return rtn;
	}
	
	public String getThumbURL(int position) {
		String rtn = "";
		if(m_Loaded)
			rtn =  m_PopList.get(position).getImages().getThumbnail().getImageUrl(); 
		return rtn;
	}
	
}
