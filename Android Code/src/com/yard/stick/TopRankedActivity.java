package com.yard.stick;

import com.yard.stick.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class TopRankedActivity extends ListActivity {

	private ImageAdapter m_ImageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.newest);
		m_ImageAdapter = new ImageAdapter(this, 1);
		m_ImageAdapter.loadImageList();

		setListAdapter(m_ImageAdapter);
		TextView txtView = (TextView) findViewById(R.id.txtSelect);
		txtView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				m_ImageAdapter.loadImageList();

			}

		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent myIntent = new Intent(v.getContext(), VotingActivity.class);
		myIntent.putExtra("ImageItem", m_ImageAdapter.getImageItem(position).getJSON());
		startActivityForResult(myIntent, 0);

	}

}
